package com.grad.akemha.dto.post;

import com.grad.akemha.entity.Post;
import com.grad.akemha.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
    private Long id;
    private DoctorResponse doctor;
    private String imageUrl;
    private String description;
    private int likesCount;
    private int commentsCount;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.doctor = new DoctorResponse(post.getUser());
        this.imageUrl = post.getImageUrl();
        this.description = post.getText();
        this.likesCount = post.getLikes().size();
        this.commentsCount = post.getComments().size();
    }
}
