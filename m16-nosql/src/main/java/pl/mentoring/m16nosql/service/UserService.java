package pl.mentoring.m16nosql.service;

import org.springframework.stereotype.Service;
import pl.mentoring.m16nosql.entity.Sport;
import pl.mentoring.m16nosql.entity.User;
import pl.mentoring.m16nosql.repository.UserRepository;

import java.util.List;

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
            .orElseGet(() -> new User(id));
    }

    public List<User> findUserEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User addSportToUser(String userId, Sport sport) {
        User user = findUserById(userId);
        user.addSport(sport);
        return userRepository.save(user);
    }

    public List<User> findUsersBySportName(String sportName) {
        return userRepository.findBySportName(sportName);
    }

}
