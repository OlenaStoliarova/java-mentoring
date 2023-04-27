package pl.mentoring.currencyexchange.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.mentoring.currencyexchange.exception.DifferentCurrencyMoneyOperationException;
import pl.mentoring.currencyexchange.exception.InsufficientFundsException;
import pl.mentoring.currencyexchange.exception.NoEntityFoundException;
import pl.mentoring.currencyexchange.model.Account;
import pl.mentoring.currencyexchange.model.AccountHolder;
import pl.mentoring.currencyexchange.model.BuyRequest;
import pl.mentoring.currencyexchange.model.Currency;
import pl.mentoring.currencyexchange.model.ExchangeRate;
import pl.mentoring.currencyexchange.model.ExchangeRequest;
import pl.mentoring.currencyexchange.model.MoneyAmount;
import pl.mentoring.currencyexchange.model.SellRequest;

import java.io.IOException;
import java.math.RoundingMode;

@Service
@AllArgsConstructor
public class ExchangeService {

    private static final long THE_BANK_ENTITY_ID = 0L;

    public static final int PRECISION = 4;

    private static final Logger logger = LoggerFactory.getLogger(ExchangeService.class);

    private final AccountHolderService accountHolderService;

    private final ExchangeRateService exchangeRateService;

    public void commitExchange(SellRequest exchangeRequest) throws IOException {
        logger.info("Thread {}. Started SellRequest {}", Thread.currentThread().getId(), exchangeRequest.getId());

        loadGreedily(exchangeRequest);

        logger.info("Thread {}. Client {} wants to sell {} from {}", Thread.currentThread().getId(),
            exchangeRequest.getClient(), exchangeRequest.getAmountToSell(), exchangeRequest.getFromAccount());

        validateClientGives(exchangeRequest.getAmountToSell(), exchangeRequest.getFromAccount().getMoneyAmount());

        ExchangeRate exchangeRate = exchangeRateService.getExchangeRate(exchangeRequest);

        MoneyAmount exchangeAmount = new MoneyAmount(exchangeRequest.getToAccount().getCurrency(),
            exchangeRequest.getAmountToSell().getAmount()
                .multiply(exchangeRate.getClientGets().getAmount())
                .divide(exchangeRate.getClientGives().getAmount(), PRECISION, RoundingMode.HALF_UP));

        validateClientGets(exchangeAmount, exchangeRequest.getToAccount().getMoneyAmount());

        commitExchange(exchangeRequest, exchangeRequest.getAmountToSell(), exchangeAmount);
    }

    public void commitExchange(BuyRequest exchangeRequest) throws IOException {
        logger.info("Thread {}. Started BuyRequest {}", Thread.currentThread().getId(), exchangeRequest.getId());

        loadGreedily(exchangeRequest);

        validateClientGets(exchangeRequest.getAmountToBuy(), exchangeRequest.getToAccount().getMoneyAmount());

        ExchangeRate exchangeRate = exchangeRateService.getExchangeRate(exchangeRequest);

        MoneyAmount exchangeAmount = new MoneyAmount(exchangeRequest.getFromAccount().getCurrency(),
            exchangeRequest.getAmountToBuy().getAmount()
                .multiply(exchangeRate.getClientGives().getAmount())
                .divide(exchangeRate.getClientGets().getAmount(), PRECISION, RoundingMode.HALF_UP));

        logger.info("Thread {}. Client {} wants to buy {}. Needs to pays {} from {}", Thread.currentThread().getId(),
            exchangeRequest.getClient(), exchangeRequest.getAmountToBuy(), exchangeAmount, exchangeRequest.getFromAccount());

        validateClientGives(exchangeAmount, exchangeRequest.getFromAccount().getMoneyAmount());

        commitExchange(exchangeRequest, exchangeAmount, exchangeRequest.getAmountToBuy());
    }

    private void commitExchange(ExchangeRequest exchangeRequest, MoneyAmount clientGivesAmount, MoneyAmount clientGetsAmount) throws IOException {
        try {
            AccountHolder theBank = accountHolderService.acquireAccountHolder(THE_BANK_ENTITY_ID);
            AccountHolder client = accountHolderService.acquireAccountHolder(exchangeRequest.getClient().getId());

            performMoneyTransaction(theBank, client, exchangeRequest, clientGivesAmount, clientGetsAmount);

            accountHolderService.saveLockedAccountHolder(client);
            accountHolderService.saveLockedAccountHolder(theBank);
            logger.info("Thread {}. Successfully exchanged {} to {} for client {}. ExchangeRequest {}", Thread.currentThread().getId(),
                clientGivesAmount, clientGetsAmount, exchangeRequest.getClient(), exchangeRequest.getId());
        } finally {
            accountHolderService.releaseAccountHolder(exchangeRequest.getClient().getId());
            accountHolderService.releaseAccountHolder(THE_BANK_ENTITY_ID);
        }
    }

    private void loadGreedily(ExchangeRequest exchangeRequest) {
        Account fullFromAccount = accountHolderService.readAccount(exchangeRequest.getClient().getId(),
            exchangeRequest.getFromAccount().getAccountNumber());
        Account fullToAccount = accountHolderService.readAccount(exchangeRequest.getClient().getId(),
            exchangeRequest.getToAccount().getAccountNumber());

        exchangeRequest.setFromAccount(fullFromAccount);
        exchangeRequest.setToAccount(fullToAccount);
    }

    private static void performMoneyTransaction(AccountHolder theBank, AccountHolder client, ExchangeRequest exchangeRequest,
                                                MoneyAmount clientGivesAmount, MoneyAmount clientGetsAmount) {
        Account clientCurrencyOneAccount = client.getAccounts().get(exchangeRequest.getFromAccount().getAccountNumber());
        Account clientCurrencyTwoAccount = client.getAccounts().get(exchangeRequest.getToAccount().getAccountNumber());
        Account banksCurrencyOneAccount = getTheBanksAccountInNeededCurrency(theBank, clientCurrencyOneAccount.getCurrency());
        Account banksCurrencyTwoAccount = getTheBanksAccountInNeededCurrency(theBank, clientCurrencyTwoAccount.getCurrency());

        clientCurrencyOneAccount.getMoneyAmount().subtract(clientGivesAmount);
        banksCurrencyOneAccount.getMoneyAmount().add(clientGivesAmount);
        banksCurrencyTwoAccount.getMoneyAmount().subtract(clientGetsAmount);
        clientCurrencyTwoAccount.getMoneyAmount().add(clientGetsAmount);
    }

    private static Account getTheBanksAccountInNeededCurrency(AccountHolder theBank, Currency currency) {
        return theBank.getAccounts().values().stream()
            .filter(account -> account.getCurrency().equals(currency))
            .findFirst()
            .orElseThrow(() -> new NoEntityFoundException(
                "The Bank does not have account for currency " + currency.getCode()));
    }

    private void validateClientGives(MoneyAmount amount, MoneyAmount fromAccount) {
        validateCurrency(amount, fromAccount, "Attempt to withdraw %s from %s account");

        if (amount.getAmount().compareTo(fromAccount.getAmount()) > 0) {
            String message = String.format("Attempt to withdraw %s from account with insufficient funds %s", amount, fromAccount);
            logger.error("Thread {}. {}", Thread.currentThread().getId(), message);
            throw new InsufficientFundsException(message);
        }
    }

    private void validateClientGets(MoneyAmount amount, MoneyAmount toAccount) {
        validateCurrency(amount, toAccount, "Attempt to put %s to %s account");
    }

    private void validateCurrency(MoneyAmount amount, MoneyAmount account, String errorMessage) {
        if (!amount.getCurrency().equals(account.getCurrency())) {
            String message = String.format(errorMessage, amount.getCurrency().getCode(), account.getCurrency().getCode());
            logger.error("Thread {}. {}", Thread.currentThread().getId(), message);
            throw new DifferentCurrencyMoneyOperationException(message);
        }
    }
}
