package pl.mentoring.springsecurityapp1.event;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;
import pl.mentoring.springsecurityapp1.service.BruteForceProtectionService;

import javax.annotation.Resource;


@Component
public class AuthenticationFailureListener  implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    @Resource(name = "bruteForceProtectionService")
    private BruteForceProtectionService bruteForceProtectionService;

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        String username = event.getAuthentication().getName();
        bruteForceProtectionService.registerLoginFailure(username);
    }
}
