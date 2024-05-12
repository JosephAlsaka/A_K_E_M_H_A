package com.grad.akemha.dto.doctor;
import com.grad.akemha.entity.enums.Gender;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddDoctorRequest {
    private String name;
    private String email;
    private String password;
    private Gender gender;
    private String specialization;

}
