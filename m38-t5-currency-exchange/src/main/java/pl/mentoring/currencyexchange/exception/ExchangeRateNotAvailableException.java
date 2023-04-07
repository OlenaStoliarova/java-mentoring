package pl.mentoring.currencyexchange.exception;

public class ExchangeRateNotAvailableException extends RuntimeException {

    public ExchangeRateNotAvailableException(String message) {
        super(message);
    }
}
