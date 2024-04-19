package com.grad.akemha.dto.post;


import com.grad.akemha.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorResponse {
    private Long id;
    private String name;
    private String email;
    private String profileImage;

    public DoctorResponse(User doctor) {
        this.id = doctor.getId();
        this.name = doctor.getName();
        this.email = doctor.getEmail();
        this.profileImage = doctor.getProfileImage();
    }
}
