package pl.mentoring.springsecurityapp1.event;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import pl.mentoring.springsecurityapp1.service.BruteForceProtectionService;

import javax.annotation.Resource;

@Component
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Resource(name = "bruteForceProtectionService")
    private BruteForceProtectionService bruteForceProtectionService;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        String username = event.getAuthentication().getName();
        bruteForceProtectionService.resetBruteForceCounter(username);
    }
}
