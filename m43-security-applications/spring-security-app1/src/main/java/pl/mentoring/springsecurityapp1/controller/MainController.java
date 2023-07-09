package pl.mentoring.springsecurityapp1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/about")
    public String getAbout() {
        return "About this app: only authorized users can see /info";
    }

    @GetMapping("/info")
    public String getInfo() {
        return "MVC APPLICATION";
    }
}
