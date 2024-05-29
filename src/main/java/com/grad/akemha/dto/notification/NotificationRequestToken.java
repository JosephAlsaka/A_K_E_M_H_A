package com.grad.akemha.dto.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequestToken {
    private String title;
    private String body;
//    private String topic;
    private String deviceToken;
}
