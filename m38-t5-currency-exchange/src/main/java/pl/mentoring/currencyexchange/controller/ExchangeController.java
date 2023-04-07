package pl.mentoring.currencyexchange.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mentoring.currencyexchange.model.AccountHolder;
import pl.mentoring.currencyexchange.model.ExchangeRate;
import pl.mentoring.currencyexchange.model.ExchangeRequest;
import pl.mentoring.currencyexchange.service.ExchangeOperationsProcessingService;
import pl.mentoring.currencyexchange.util.SampleDataLoader;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/currencyExchange")
@AllArgsConstructor
public class ExchangeController {

    private static final Logger logger = LoggerFactory.getLogger(ExchangeController.class);

    private final ObjectMapper objectMapper;
    private final ExchangeOperationsProcessingService exchangeOperationsProcessingService;

    @GetMapping(value = "runSampleExchanges")
    public void runSampleExchangesInParallel() {
        SampleDataLoader<ExchangeRequest[]> exchangeRequestSampleDataLoader = new SampleDataLoader<>(objectMapper);

        List<ExchangeRequest> exchangeRequests = SampleDataLoader.getEntitiesList(exchangeRequestSampleDataLoader
            .getEntityFromSampleDataFile("exchange_requests.json", ExchangeRequest[].class));

        exchangeOperationsProcessingService.processExchangeOperationsInParallel(exchangeRequests, 4);
    }

    @GetMapping(value = "addSampleData")
    public void addSampleAccountsAndRates() throws IOException {
        SampleDataLoader<AccountHolder> accountHolderSampleDataLoader = new SampleDataLoader<>(objectMapper);

        for (int i = 0; i <= 3; i++) {
            String filename = i + ".json";
            AccountHolder entityFromSampleData = accountHolderSampleDataLoader.getEntityFromSampleDataFile(filename, AccountHolder.class);
            File file = new File(filename);
            objectMapper.writeValue(file, entityFromSampleData);
        }

        SampleDataLoader<ExchangeRate[]> exchangeRateSampleDataLoader = new SampleDataLoader<>(objectMapper);
        ExchangeRate[] exchangeRates = exchangeRateSampleDataLoader.getEntityFromSampleDataFile(
            "exchange_rates.json", ExchangeRate[].class);
        objectMapper.writeValue(new File("exchange_rates.json"), exchangeRates);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> clientError(RuntimeException exception) {
        logger.error(exception.getMessage(), exception);
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
