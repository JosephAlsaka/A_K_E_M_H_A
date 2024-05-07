package com.grad.akemha.dto.post;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
//import org.springframework.lang.Nullable; // Importing @Nullable annotation

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    @NotNull(message = "description can't be Null")
    private String description;
    @NotNull(message = "Image can't be Null")
    private MultipartFile image;
}
