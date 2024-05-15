package com.grad.akemha.dto.beneficiary;

import com.grad.akemha.entity.enums.Gender;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddBeneficiaryRequest {
    private String name;
    private String email;
    private String password;
    private Gender gender;
}
