package pl.mentoring.m16nosql.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mentoring.m16nosql.entity.Sport;
import pl.mentoring.m16nosql.entity.User;
import pl.mentoring.m16nosql.service.UserService;

import java.util.List;

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
    public User findUserById(@PathVariable String id) {
        return userService.findUserById(id);
    }

    @GetMapping("/email/{email}")
    public List<User> findUserByEmail(@PathVariable String email) {
        return userService.findUserEmail(email);
    }

    @PutMapping("/{userId}/sport")
    public User findUserById(@PathVariable String userId,
                             @RequestBody Sport sport) {
        return userService.addSportToUser(userId, sport);
    }
}