package com.grad.akemha.bootstrap;

import com.grad.akemha.entity.User;
import com.grad.akemha.repository.UserRepository;
import com.grad.akemha.repository.VerificationCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Transactional
public class AccountCleanupTask {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    // Define the time interval for running the cleanup task (e.g., every minute)
    @Scheduled(cron = "*/25 * * * * *") // Runs every minute
    public void cleanupUnverifiedAccounts() {
        // Define the time limit for account verification (e.g., 1 minute ago)
        LocalDateTime verificationTimeLimit = LocalDateTime.now().minusMinutes(1);

        // Retrieve unverified accounts registered before the verification time limit
        List<User> unverifiedAccounts = userRepository.findUnverifiedAccountsCreatedBefore(verificationTimeLimit);

        // Delete unverified accounts from the database
        for (User user : unverifiedAccounts) {
            System.out.println(user.getId());
            // Delete verification code if exists for the user
            verificationCodeRepository.deleteByUser(user);

            userRepository.delete(user);
            System.out.println("DELETEDDD");
        }
    }
}

