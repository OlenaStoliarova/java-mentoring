package pl.mentoring.currencyexchange.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import pl.mentoring.currencyexchange.exception.ExchangeRateNotAvailableException;
import pl.mentoring.currencyexchange.model.Currency;
import pl.mentoring.currencyexchange.model.ExchangeRate;
import pl.mentoring.currencyexchange.model.ExchangeRequest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ExchangeRateService {

    private final List<ExchangeRate> exchangeRates;

    private final ObjectMapper objectMapper;

    public ExchangeRateService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.exchangeRates = getExchangeRates();
    }

    public ExchangeRate getExchangeRate(ExchangeRequest exchangeRequest) {
        return getExchangeRate(exchangeRequest.getFromAccount().getCurrency(),
            exchangeRequest.getToAccount().getCurrency());
    }

    private ExchangeRate getExchangeRate(Currency from, Currency to) {
        return this.exchangeRates.stream()
            .filter(rate -> rate.getClientGives().getCurrency().equals(from)
                && rate.getClientGets().getCurrency().equals(to))
            .findFirst()
            .orElseThrow(() -> new ExchangeRateNotAvailableException(
                "No exchange rate from " + from.getCode() + " to " + to.getCode() + " available"));
    }

    private List<ExchangeRate> getExchangeRates() {
        try {
            ExchangeRate[] rates = objectMapper.readValue(new File("exchange_rates.json"), ExchangeRate[].class);
            if (rates != null) {
                return Arrays.asList(rates);
            } else {
                return new ArrayList<>();
            }
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
}
