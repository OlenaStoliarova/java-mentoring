package pl.mentoring.microservices.feignclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@EnableFeignClients
@Controller
public class FeignClientApplication {

	private static final Logger logger = LoggerFactory.getLogger(FeignClientApplication.class);

	@Autowired
	GreetingClient greetingClient;

	@Value("${eureka.instance.instance-id}")
	String instance;

	public static void main(String[] args) {
		SpringApplication.run(FeignClientApplication.class, args);
	}

	@RequestMapping("/get-greeting")
	public String greeting(Model model) {
		logger.info("FeignClientApplication - get-greeting");
		model.addAttribute("greeting", greetingClient.greeting());
		model.addAttribute("instance", instance);
		return "greeting-view";
	}
}
