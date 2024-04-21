package com.grad.akemha.dto.activity;

import com.grad.akemha.entity.Activity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityResponse {
    private Long id;
    private String imageUrl;
    private String description;

    public ActivityResponse(Activity activity) {
        this.id = activity.getId();
        this.description = activity.getDescription();
        this.imageUrl = activity.getImageUrl();
    }
}
