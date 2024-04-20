package com.grad.akemha.dto.medicalDevice;

import com.grad.akemha.entity.enums.Gender;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddDeviceRequest {
    private String name;
    private Integer count;
}
