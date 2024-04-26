//package com.grad.akemha.service;
//
//import com.grad.akemha.entity.User;
//import com.grad.akemha.entity.VerificationCode;
//import com.grad.akemha.repository.VerificationCodeRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//
//@Service
//@RequiredArgsConstructor
//public class VerificationCodeService {
//
//    private final WhatsAppService whatsAppService;
//
//    public void sendVerificationCode(User user) {
//
//        // Set expiration time (e.g., 3 hours from now)
////        LocalDateTime expiryTime = LocalDateTime.now().plusHours(3);
//
//
//        // Send the code to the user (e.g., via email or SMS)
//        sendCodeToUser(user, code);
//    }
//}
