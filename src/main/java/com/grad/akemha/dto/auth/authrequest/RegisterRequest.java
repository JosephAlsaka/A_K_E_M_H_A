package com.grad.akemha.dto.auth.authRequest;


import com.grad.akemha.entity.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

//    @NotBlank(message = "Please add a user name")

    @NotBlank(message = "Please add a name")
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private LocalDate dob;
    private Role role;
}
