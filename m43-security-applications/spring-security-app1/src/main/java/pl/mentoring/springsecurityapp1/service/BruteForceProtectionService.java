package pl.mentoring.springsecurityapp1.service;

public interface BruteForceProtectionService {

    void registerLoginFailure(String username);

    void resetBruteForceCounter(String username);

}
