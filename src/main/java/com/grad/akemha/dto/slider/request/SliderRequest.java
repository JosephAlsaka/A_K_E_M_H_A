package com.grad.akemha.dto.slider.request;

import org.springframework.web.multipart.MultipartFile;

public record SliderRequest(MultipartFile image, String pageLink) {
}
