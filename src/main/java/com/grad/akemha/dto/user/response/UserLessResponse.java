package com.grad.akemha.dto.user.response;

import com.grad.akemha.dto.consultation.consultationResponse.ConsultationRes;
import com.grad.akemha.entity.Consultation;
import com.grad.akemha.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLessResponse {
    private Long id;
    private String name;
    private String email;
    private String profileImg;

    public UserLessResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.profileImg = user.getProfileImage();
    }
}
