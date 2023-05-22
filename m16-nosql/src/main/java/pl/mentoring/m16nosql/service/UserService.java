package pl.mentoring.m16nosql.service;

import org.springframework.stereotype.Service;
import pl.mentoring.m16nosql.entity.User;
import pl.mentoring.m16nosql.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createNewUser(User newUser) {
        userRepository.save(newUser);
    }

    public User findUserById(String id) {
        return userRepository.findById(id)
            .orElseGet(User::new);
    }
}
