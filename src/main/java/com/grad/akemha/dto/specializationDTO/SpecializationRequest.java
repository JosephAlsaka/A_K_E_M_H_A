package com.grad.akemha.dto.specializationDTO;

import org.springframework.web.multipart.MultipartFile;

public record SpecializationRequest(String specializationType, Boolean isPublic, MultipartFile image) {
}
