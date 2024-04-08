package com.grad.akemha.dto;
import com.grad.akemha.entity.enums.Gender;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class AddDoctorDto {
    private String name;
    private String email;
    private Long password;
    private Gender gender;
    private String specialization;
}
