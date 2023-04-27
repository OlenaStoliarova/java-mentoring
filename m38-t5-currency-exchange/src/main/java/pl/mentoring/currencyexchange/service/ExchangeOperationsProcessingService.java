package pl.mentoring.currencyexchange.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.mentoring.currencyexchange.model.BuyRequest;
import pl.mentoring.currencyexchange.model.ExchangeRequest;
import pl.mentoring.currencyexchange.model.SellRequest;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ExchangeOperationsProcessingService {

    private static final Logger logger = LoggerFactory.getLogger(ExchangeOperationsProcessingService.class);

    private final ExchangeService exchangeService;

    public void processExchangeOperationsInParallel(List<ExchangeRequest> exchangeRequests, int numberOfThreads) {
        try {
            ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
            List<Callable<Void>> tasks = createCurrencyExchangeTasks(exchangeRequests);
            executorService.invokeAll(tasks);
        } catch (InterruptedException e) {
            logger.warn("processExchangeOperationsInParallel failed", e);
            Thread.currentThread().interrupt();
        }
    }

    private List<Callable<Void>> createCurrencyExchangeTasks(List<ExchangeRequest> exchangeRequests) {
        return exchangeRequests.stream()
            .map(this::createTask)
            .collect(Collectors.toList());
    }

    private Callable<Void> createTask(ExchangeRequest exchangeRequest) {
        return () -> {
            try {
                if (exchangeRequest instanceof SellRequest) {
                    exchangeService.commitExchange((SellRequest) exchangeRequest);
                } else {
                    exchangeService.commitExchange((BuyRequest) exchangeRequest);
                }
            } catch (Exception e) {
                logger.error("Thread {}: exchangeRequest {} failed", Thread.currentThread().getId(), exchangeRequest.getId(), e);
            }
            return null;
        };
    }
}
