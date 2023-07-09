package pl.mentoring.springsecurityapp1.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping(value = {"/"})
    public String showIndexPage() {
        return "index";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping(value = {"/main"})
    public String showMainPage(Model model, Authentication authentication) {
        model.addAttribute("userName", authentication.getName());
        model.addAttribute("hasUserRole",
            authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
        model.addAttribute("hasAdminRole",
            authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")));

        return "main";
    }

}
