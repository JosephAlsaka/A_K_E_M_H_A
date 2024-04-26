package com.grad.akemha.controller;

import com.grad.akemha.dto.BaseResponse;
import com.grad.akemha.dto.auth.authRequest.LoginRequest;
import com.grad.akemha.dto.auth.authRequest.RegisterRequest;
import com.grad.akemha.dto.auth.authResponse.AuthResponse;
import com.grad.akemha.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    //s
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    private final AuthenticationService authService;


    //FIXME fix the return type
    @PostMapping("/register")
    public ResponseEntity<BaseResponse<String>> register(
            @Valid @RequestBody RegisterRequest request
    ) throws IOException {
        try {
            String response = authService.register(request);
            return ResponseEntity.ok()
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "User registered successfully", response));
        } catch (IOException e) {
            System.out.println("============================ in catch");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BaseResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<AuthResponse>> login(
            @RequestBody LoginRequest request
    ) {
//        try {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok()
                .body(new BaseResponse<>(HttpStatus.OK.value(), "User logged successfully", response));
//        } catch (BadCredentialsException e) {
//            // Handle user not found exception
//            //another way
//           /* Map<String, String> errorResponse = new HashMap<>();
//            errorResponse.put("msg", "User not found");   then put errorResponse in .body(errorResponse) */
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body(new BaseResponse<>(HttpStatus.NOT_FOUND.value(), "user not found",null));
//        }
//    }
    }

}
