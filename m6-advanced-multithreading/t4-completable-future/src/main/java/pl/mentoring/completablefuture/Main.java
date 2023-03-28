package pl.mentoring.completablefuture;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletionStageService completionStageService = new CompletionStageService(new EmployeeService());

        CompletionStage<Void> complete = completionStageService.getHiredEmployeesAsync().thenAccept(employeeList -> {
            List<CompletableFuture<Employee>> employeesWithSalary = employeeList.parallelStream()
                .map(completionStageService::getEmployeeWithSalaryAsync)
                .map(CompletionStage::toCompletableFuture)
                .collect(Collectors.toList());

            CompletionStage<Void> allEmployeesAreWithSalaries = CompletableFuture.allOf(employeesWithSalary.toArray(new CompletableFuture[employeesWithSalary.size()]))
                .thenAccept(v -> {
                    System.out.println(java.time.LocalTime.now() + " all salaries are ready");
                    employeesWithSalary.stream()
                        .map(CompletableFuture::join)
                        .forEach(System.out::println);
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
