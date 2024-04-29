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
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    //s
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    private final AuthenticationService authService;

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<AuthResponse>> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        AuthResponse response = authService.register(request);
//        try {
        return ResponseEntity.ok()
                .body(new BaseResponse<>(HttpStatus.OK.value(), "User registered successfully", response));
//        } catch (Exception e) {
//            System.out.println("============================ in catch");
//
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(new BaseResponse<>(HttpStatus.BAD_REQUEST.value(), "email is taken",null));
//        }
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
