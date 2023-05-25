package pl.mentoring.microservices.uiapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
public class UiAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(UiAppApplication.class, args);
    }

}
