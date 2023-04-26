package pl.mentoring.microservices.twosleuthtest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SleuthController {

    public static final String SUCCESS = "success";

    @Autowired
    private SleuthService sleuthService;

    @Autowired
    GreetingClient greetingClient;

    private static final Logger logger = LoggerFactory.getLogger(SleuthController.class);

    @GetMapping("/")
    public String helloSleuth() {
        logger.info("Hello Sleuth");
        return SUCCESS;
    }

    @GetMapping("/same-span")
    public String helloSleuthSameSpan() throws InterruptedException {
        logger.info("Same Span");
        sleuthService.doSomeWorkSameSpan();
        return SUCCESS;
    }

    @GetMapping("/new-span")
    public String helloSleuthNewSpan() throws InterruptedException {
        logger.info("New Span");
        sleuthService.doSomeWorkNewSpan();
        return SUCCESS;
    }

    @GetMapping("/sleuthGreeting")
    public String helloSleuthCallAnotherMicroservice() {
        logger.info("Sleuth-test calling another microservice");
        return greetingClient.greeting();
    }
}
