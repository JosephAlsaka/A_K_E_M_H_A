package com.grad.akemha.dto.medicalDevice;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReserveDeviceRequest {
    private Integer quantity;
    private Long medicalDeviceId;
}
