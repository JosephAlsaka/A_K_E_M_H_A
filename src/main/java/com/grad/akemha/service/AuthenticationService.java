package com.grad.akemha.service;


import com.grad.akemha.dto.auth.authrequest.LoginRequest;
import com.grad.akemha.dto.auth.authrequest.RegisterRequest;
import com.grad.akemha.dto.auth.authrequest.VerificationRequest;
import com.grad.akemha.dto.auth.authresponse.AuthResponse;
import com.grad.akemha.entity.Token;
import com.grad.akemha.entity.User;
import com.grad.akemha.entity.VerificationCode;
import com.grad.akemha.exception.ForbiddenException;
import com.grad.akemha.exception.NotFoundException;
import com.grad.akemha.exception.authExceptions.EmailAlreadyExistsException;
import com.grad.akemha.exception.authExceptions.UserNotFoundException;
import com.grad.akemha.repository.TokenRepository;
import com.grad.akemha.exception.authExceptions.WrongPasswordException;
import com.grad.akemha.repository.UserRepository;
import com.grad.akemha.repository.VerificationCodeRepository;
import com.grad.akemha.security.JwtService;
import com.grad.akemha.utils.AESEncryptionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final WhatsAppService whatsAppService;
    private final VerificationCodeRepository verificationCodeRepository;
    private final FCMService fcmService;
    private final DeviceTokenService deviceTokenService;
    private final TokenRepository tokenRepository;

    public String register(RegisterRequest request) throws IOException {
        if (userAlreadyExists(request.getEmail())) {
            throw new EmailAlreadyExistsException("User already exists");
        }

        var user = User.builder().name(request.getName()).email(request.getEmail()).phoneNumber(request.getPhoneNumber()).dob(request.getDob()).password(passwordEncoder.encode(request.getPassword())).role(request.getRole()).isActive(true).isVerified(false).creationDate(LocalDateTime.now()).build();

        userRepository.save(user);

        // after I save the user I send him the code
        whatsAppService.sendVerificationCode(user);

        return user.getId().toString();
    }

    private boolean userAlreadyExists(String email) {
        return userRepository.existsByEmail(email);
    }


    public AuthResponse login(LoginRequest request) throws BadCredentialsException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UserNotFoundException("User not found"));
            // adding a condition when the user is trying to log in, and he is not verified
            if (!user.getIsVerified()) {
                throw new ForbiddenException("The User is not verified");
            }

            if (deviceTokenService.saveDeviceTokenIfNotExists(request.getDeviceToken(), user)) {
                fcmService.subscribeToTopic(request.getDeviceToken(), "all");
                fcmService.subscribeToTopic(request.getDeviceToken(), "posts");
            }
            boolean result = passwordEncoder.matches(request.getPassword(), user.getPassword());
            if(!result){
                throw new WrongPasswordException("The password is wrong");
            }
            var jwtToken = jwtService.generateToken(user);
            // to save token in token entity
            saveUserToken(user, jwtToken);
            return AuthResponse.builder().token(jwtToken)
                    .id(user.getId())
                    .userEmail(user.getEmail())
                    .role(user.getRole().toString())
                    .role(user.getRole())
                    .build();
            //another way
////        final AuthResponse authResponseModel;
////        authResponseModel = new AuthResponseModel(
////                jwtToken,
////                HttpStatus.OK.value(),
////                user.getEmail(),
////                "Successfully logged in"
////        );
        } catch (BadCredentialsException e) {
            throw new UserNotFoundException("User not found");
        }
    }


    public AuthResponse verifyAccount(VerificationRequest verificationRequest) throws Exception {
        Optional<User> optionalUser = userRepository.findById(verificationRequest.getUserId());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            VerificationCode verificationCodeEntity = verificationCodeRepository.findByUser(user).orElseThrow();
            String storedCode = AESEncryptionUtil.decrypt(verificationCodeEntity.getCode());
            System.out.println(storedCode);

            if (Objects.equals(storedCode, verificationRequest.getCode())) {
                user.setIsVerified(true);
                userRepository.save(user);
                var jwtToken = jwtService.generateToken(user);

                // TODO: subscribe to topics
                if (deviceTokenService.saveDeviceTokenIfNotExists(verificationRequest.getDeviceToken(), user)) {
                    // todo: subscribe to topics
                    fcmService.subscribeToTopic(verificationRequest.getDeviceToken(), "all");
                    fcmService.subscribeToTopic(verificationRequest.getDeviceToken(), "posts");
                }
                // to save token in token entity
                saveUserToken(user, jwtToken);
                return AuthResponse.builder()
                        .token(jwtToken)
                        .id(user.getId())
                        .userEmail(user.getEmail())
                        .role(user.getRole().toString())
                        .build();
            } else {
                throw new ForbiddenException("The Code You've entered " + verificationRequest.getCode() + " does not match the code Sent to your WhatsApp number");
            }

        } else {
            throw new NotFoundException("User Not Found With the Id of: " + verificationRequest.getUserId());
        }
    }


    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .expiryTime(new Date(System.currentTimeMillis() + (1000 * 3600 * 24 * 7)))
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }
}

