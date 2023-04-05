package pl.mentoring.completablefuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletionStageService completionStageService = new CompletionStageService(new EmployeeService());

        CompletionStage<Void> complete = completionStageService.getHiredEmployeesAsync().thenAccept(employeeList -> {
            List<CompletableFuture<Employee>> employeesWithSalary = employeeList.parallelStream()
                .map(completionStageService::getEmployeeWithSalaryAsync)
                .map(CompletionStage::toCompletableFuture)
                .collect(Collectors.toList());

            CompletionStage<Void> allEmployeesAreWithSalaries = CompletableFuture.allOf(employeesWithSalary.toArray(new CompletableFuture[employeesWithSalary.size()]))
                .thenAccept(v -> {
                    logger.info("{} all salaries are ready", java.time.LocalTime.now());
                    employeesWithSalary.stream()
                        .map(CompletableFuture::join)
                        .forEach(employee -> logger.info("{}", employee));
                });

            try {
                allEmployeesAreWithSalaries.toCompletableFuture().get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });

        complete.toCompletableFuture().get();
    }
}
