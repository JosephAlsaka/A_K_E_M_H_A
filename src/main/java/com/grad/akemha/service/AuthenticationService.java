package com.grad.akemha.service;

import com.grad.akemha.dto.auth.authrequest.LoginRequest;
import com.grad.akemha.dto.auth.authrequest.RegisterRequest;
import com.grad.akemha.dto.auth.authresponse.AuthResponse;
import com.grad.akemha.entity.User;
import com.grad.akemha.repository.UserRepository;
import com.grad.akemha.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request)
//            throws RegistrationException
    {
//        if (userAlreadyExists(request.getEmail())) {
//            throw new EmailAlreadyExistsException("User already exists");
//        }

        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .dob(request.getDob())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .isActive(true)
                .build();

        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .userEmail(user.getEmail())
                .build();
    }

    private boolean userAlreadyExists(String email) {
        return userRepository.existsByEmail(email);
    }

//    public AuthResponse login(LoginRequest request) throws BadCredentialsException {
////        try {
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        request.getEmail(),
//                        request.getPassword()
//                )
//        );
//        var user = userRepository.findByEmail(request.getEmail())
//                .orElseThrow(() -> new UserNotFoundException("User not found"));
//
//        var jwtToken = jwtService.generateToken(user);
//        final AuthResponse authResponseModel;
//        return
//                AuthResponse.builder()
//                        .token(jwtToken)
//                        .userEmail(user.getEmail())
//                        .build();
////        authResponseModel = new AuthResponseModel(
////                jwtToken,
////                HttpStatus.OK.value(),
////                user.getEmail(),
////                "Successfully logged in"
////        );
////        } catch (BadCredentialsException e){
////            System.out.println("i am in catch");
////            return AuthenticationResponse.builder()
////                    .errorMessage("User not found")
////                    .build();
////        }
//    }
}
