package pl.mentoring.m16nosql.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mentoring.m16nosql.entity.User;
import pl.mentoring.m16nosql.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public void createUser(@RequestBody User newUser) {
        userService.createNewUser(newUser);
    }


    @GetMapping("/{id}")
    public User runSampleExchangesInParallel(@PathVariable String id) {
        return userService.findUserById(id);
    }
}