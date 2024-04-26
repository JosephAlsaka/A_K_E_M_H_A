package com.grad.akemha.service;

import com.grad.akemha.entity.User;
import com.grad.akemha.entity.VerificationCode;
import com.grad.akemha.repository.VerificationCodeRepository;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Random;

@Service
public class WhatsAppService {

    private static final String BASE_URL = "https://api.ultramsg.com/instance84323/messages/chat";
    private static final Random RANDOM = new Random();
    private final OkHttpClient client = new OkHttpClient();

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    public String sendVerificationCode(User user) throws IOException {
        String code = generateVerificationCode();

        RequestBody body = new FormBody.Builder()
                .add("token", "2yauqhb7db2b6zod")
                .add("to", user.getPhoneNumber())
                .add("body", "Your Akemha Code:\n" + code)
                .build();

        Request request = new Request.Builder()
                .url(BASE_URL)
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();

        //FIXME fix the the time to make it every three hours
        LocalDateTime expiryTime = LocalDateTime.now().plusSeconds(70);

        // Create VerificationCode entity and link it to the user
        VerificationCode verificationCodeEntity = new VerificationCode();
        verificationCodeEntity.setCode(code);
        verificationCodeEntity.setUser(user);
        verificationCodeEntity.setExpiryTime(expiryTime);

        // Save the verification code entity
        verificationCodeRepository.save(verificationCodeEntity);

        try (Response response = client.newCall(request).execute()) {
            System.out.println(code);
            return code;
        }
    }

    private String generateVerificationCode() {
        // Generate a random 6-digit verification code
        int code = 100000 + RANDOM.nextInt(900000);
        return String.valueOf(code);
    }
}
