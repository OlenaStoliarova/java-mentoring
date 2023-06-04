package pl.mentoring.m16nosql.service;

import org.springframework.stereotype.Service;
import pl.mentoring.m16nosql.entity.Sport;
import pl.mentoring.m16nosql.entity.User;
import pl.mentoring.m16nosql.exception.EntityNotFoundException;
import pl.mentoring.m16nosql.repository.UserRepository;
import pl.mentoring.m16nosql.repository.UserSearchQueryRepository;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserSearchQueryRepository userSearchQueryRepository;

    public UserService(UserRepository userRepository, UserSearchQueryRepository userSearchQueryRepository) {
        this.userRepository = userRepository;
        this.userSearchQueryRepository = userSearchQueryRepository;
    }

    public void createNewUser(User newUser) {
        userRepository.save(newUser);
    }

    public User findUserById(String id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("user with id " + id + " not found"));
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

    public List<User> findUsersBySearchQuery(String searchQuery) {
        List<String> foundUsersIds = userSearchQueryRepository.searchByQuery(searchQuery);

        return userRepository.findAllById(foundUsersIds);
    }
}
