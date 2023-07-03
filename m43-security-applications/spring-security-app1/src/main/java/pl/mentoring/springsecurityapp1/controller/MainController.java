package pl.mentoring.springsecurityapp1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/info")
    public String getInfo() {
        return "MVC APPLICATION";
    }
}
