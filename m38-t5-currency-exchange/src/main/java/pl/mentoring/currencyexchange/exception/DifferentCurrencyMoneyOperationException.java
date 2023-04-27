package pl.mentoring.currencyexchange.exception;

public class DifferentCurrencyMoneyOperationException extends RuntimeException {

    public DifferentCurrencyMoneyOperationException(String message) {
        super(message);
    }
}
