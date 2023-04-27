package pl.mentoring.currencyexchange.model;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.mentoring.currencyexchange.exception.DifferentCurrencyMoneyOperationException;

import java.math.BigDecimal;

@NoArgsConstructor
@EqualsAndHashCode
public class MoneyAmount {
    private Currency currency;
    private BigDecimal amount;

    public MoneyAmount(Currency currency, BigDecimal amount) {
        this.currency = currency;
        setAmount(amount);
    }

    public void add(MoneyAmount addition) {
        if (this.getCurrency().equals(addition.getCurrency())) {
            setAmount(this.amount.add(addition.getAmount()));
        } else {
            String message = String.format("Attempt to add %s and %s", this.currency.getCode(), addition.getCurrency().getCode());
            throw new DifferentCurrencyMoneyOperationException(message);
        }
    }

    public void subtract(MoneyAmount withdrawal) {
        if (this.getCurrency().equals(withdrawal.getCurrency())) {
            setAmount(this.amount.subtract(withdrawal.getAmount()));
        } else {
            String message = String.format("Attempt to subtract %s from %s", withdrawal.getCurrency().getCode(), this.currency.getCode());
            throw new DifferentCurrencyMoneyOperationException(message);
        }
    }


    public Currency getCurrency() {
        return this.currency;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("MoneyAmount cannot be negative");
        }

        this.amount = amount;
    }

    public String toString() {
        return "MoneyAmount(" + this.getAmount() + " " + this.getCurrency().getCode() + ")";
    }
}
