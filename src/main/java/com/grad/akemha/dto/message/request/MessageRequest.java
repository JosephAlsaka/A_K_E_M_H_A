package com.grad.akemha.dto.message.request;

import com.grad.akemha.entity.User;
import com.grad.akemha.entity.enums.ConsultationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequest {
    private String textMsg;
    private Long userId;
}
