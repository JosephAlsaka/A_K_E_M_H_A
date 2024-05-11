package com.grad.akemha.bootstrap;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class FCMInitializer {
    @Value("${app.firebase-configuration-file}")
    private String firebaseConfigPath;
//    Logger logger = LoggerFactory.getLogger(FCMInitializer.class);

    @PostConstruct
    public void initialize() {
        try {
            FirebaseOptions options = FirebaseOptions.builder().setCredentials(GoogleCredentials.fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())).build();
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
//                logger.info("Firebase application initialized");
            }

            // TODO: remove this 3 lines of code
//            FirebaseMessaging.getInstance().subscribeToTopicAsync(
//                    List.of("cvcy8XJ7QSGqx5ATL45Gdv:APA91bGXxkbwUDlO6Z7K97i9VurgqMzJlo76kKoLZKJLtGxhxcICxYX3wA-s9ZVI3EFGEH-oS6ePslZYQFITykzHxm5P8Gv-eXVBXQpHpKTLAfz3p1_Kk1_EIBS-iP1uzbpZ5x0D5R9f"),
//                    "sssss");
//            System.out.println("Subscribed successfully");

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
