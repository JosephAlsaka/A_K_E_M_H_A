package com.grad.akemha.dto.message.response;

import com.grad.akemha.dto.user.response.UserLessResponse;
import com.grad.akemha.entity.Message;
import com.grad.akemha.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {
    private Long id;
    private String textMessage;
    private UserLessResponse userLessResponse;

    public MessageResponse(Message message, User user){
        this.id = message.getId();
        this.textMessage = message.getTextMsg();
        this.userLessResponse = new UserLessResponse(user);
    }
}
