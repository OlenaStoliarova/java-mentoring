package pl.mentoring.microservices.twosleuthtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TwoSleuthTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TwoSleuthTestApplication.class, args);
    }

}
