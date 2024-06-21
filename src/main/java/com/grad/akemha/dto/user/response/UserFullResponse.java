package com.grad.akemha.dto.user.response;

import com.grad.akemha.entity.Specialization;
import com.grad.akemha.entity.User;
import com.grad.akemha.entity.enums.Gender;
import com.grad.akemha.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserFullResponse {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private LocalDate dob;
    private String profileImage;
    private Gender gender;
    private Role role;
    //forDoctor
    private String description;
    private String location;
    private String openingTimes;
    private Specialization specialization;

    public UserFullResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.gender = user.getGender();
        this.role = user.getRole();
        this.dob = user.getDob();
        this.profileImage = user.getProfileImage();
        if (user.getRole() == Role.DOCTOR) {
            this.description = user.getDescription();
            this.location = user.getLocation();
            this.openingTimes = user.getOpeningTimes();
            this.specialization = user.getSpecialization();
        }
    }
}
