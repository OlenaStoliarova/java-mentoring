package pl.mentoring.completablefuture;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    private final Map<String, BigDecimal> negotiatedSalaries;

    public EmployeeService() {
        negotiatedSalaries = new HashMap<>();
        negotiatedSalaries.put("1", BigDecimal.valueOf(4790.50));
        negotiatedSalaries.put("2", BigDecimal.valueOf(1900.00));
        negotiatedSalaries.put("3", BigDecimal.valueOf(4500.00));
        negotiatedSalaries.put("4", BigDecimal.valueOf(2300.50));
        negotiatedSalaries.put("5", BigDecimal.valueOf(3800.75));
        negotiatedSalaries.put("6", BigDecimal.valueOf(3500.00));
        negotiatedSalaries.put("7", BigDecimal.valueOf(1150.99));
        negotiatedSalaries.put("8", BigDecimal.valueOf(3200.00));
        negotiatedSalaries.put("9", BigDecimal.valueOf(1600.00));
        negotiatedSalaries.put("10", BigDecimal.valueOf(2800.50));
        negotiatedSalaries.put("11", BigDecimal.valueOf(7005.00));
        negotiatedSalaries.put("12", BigDecimal.valueOf(3100.00));
    }

    public List<Employee> fetchHiredEmployees() {
        logger.info("{} fetchHiredEmployees in {}", java.time.LocalTime.now(), Thread.currentThread().getId());
        ObjectMapper objectMapper = new ObjectMapper();
        putCurrentThreadToSleep(500);  // let's imagine getting un answer from REST endpoint takes some time

        URL resource = getClass().getClassLoader().getResource("hired_employees.json");
        if (resource != null) {
            try {
                File src = new File(resource.toURI());
                return Arrays.asList(objectMapper.readValue(src, Employee[].class));
            } catch (URISyntaxException | IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            return new ArrayList<>();
        }
    }

    public BigDecimal getSalary(String employeeId) {
        logger.info("{} getSalary for {} in {}", java.time.LocalTime.now(), employeeId, Thread.currentThread().getId());

        try {
            Random rand = SecureRandom.getInstanceStrong();
            putCurrentThreadToSleep(rand.nextInt(1000)); // let's imagine getting un answer from REST endpoint takes some time
        } catch (NoSuchAlgorithmException e) {
            logger.warn("SecureRandom.getInstanceStrong exception", e);
        }

        logger.info("{} salary for {} is ready", java.time.LocalTime.now(), employeeId);
        return negotiatedSalaries.get(employeeId);
    }

    private void putCurrentThreadToSleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            logger.warn("Interrupted!", e);
            // Restore interrupted state...
            Thread.currentThread().interrupt();
        }
    }
}
