package pl.mentoring.completablefuture;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class CompletionStageService {

    private final EmployeeService employeeService;

    public CompletionStageService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    CompletionStage<List<Employee>> getHiredEmployeesAsync() {
        return CompletableFuture.supplyAsync(employeeService::fetchHiredEmployees);
    }

    CompletionStage<Employee> getEmployeeWithSalaryAsync(Employee employee) {

        return getSalaryAsync(employee.getId()).thenApply(salary -> {
            employee.setSalary(salary);
            return employee;
        });
    }

    CompletionStage<BigDecimal> getSalaryAsync(String employeeId) {
        return CompletableFuture.supplyAsync(() -> employeeService.getSalary(employeeId));
    }
}
