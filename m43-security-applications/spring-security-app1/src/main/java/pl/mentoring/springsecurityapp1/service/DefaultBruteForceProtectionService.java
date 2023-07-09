package pl.mentoring.springsecurityapp1.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mentoring.springsecurityapp1.entity.User;
import pl.mentoring.springsecurityapp1.repository.UserRepository;

import java.time.LocalDateTime;

@Service("bruteForceProtectionService")
@AllArgsConstructor
public class DefaultBruteForceProtectionService implements BruteForceProtectionService {

    public static final int MAX_FAILED_LOGINS = 3;

    private UserRepository userRepository;

    @Override
    public void registerLoginFailure(String username) {
        User user = getUser(username);
        if (user != null) {
            if (Boolean.FALSE.equals(user.getLocked())) {
                int failedCounter = user.getFailedLoginAttempts();
                if (MAX_FAILED_LOGINS < failedCounter + 1) {
                    user.setLocked(true);
                    user.setLockStart(LocalDateTime.now());
                } else {
                    user.setFailedLoginAttempts(failedCounter + 1);
                }
                userRepository.save(user);
            } else {
                unlockWhenTimeExpired(user);
            }
        }
    }

    @Override
    public void resetBruteForceCounter(String username) {
        User user = getUser(username);
        if (user != null) {
            user.setFailedLoginAttempts(0);
            user.setLocked(false);
            user.setLockStart(null);
            userRepository.save(user);
        }
    }

    private boolean unlockWhenTimeExpired(User user) {
        LocalDateTime lockStart = user.getLockStart();

        if (lockStart != null && lockStart.plusMinutes(5).isBefore(LocalDateTime.now())) {
            user.setLocked(false);
            user.setLockStart(null);
            user.setFailedLoginAttempts(0);

            userRepository.save(user);

            return true;
        }

        return false;
    }

    private User getUser(final String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}
