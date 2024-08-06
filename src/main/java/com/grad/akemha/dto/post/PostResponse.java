package com.grad.akemha.dto.post;

import com.grad.akemha.entity.Post;
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
    private Boolean isLiked;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.doctor = new DoctorResponse(post.getUser());
        this.imageUrl = post.getImageUrl();
        this.description = post.getDescription();
        this.likesCount = post.getLikes().size();
        this.commentsCount = post.getComments().size();
        this.isLiked = false;
    }

    public PostResponse(Post post, Boolean isLiked) {
        this.id = post.getId();
        this.doctor = new DoctorResponse(post.getUser());
        this.imageUrl = post.getImageUrl();
        this.description = post.getDescription();
        this.likesCount = post.getLikes().size();
        this.commentsCount = post.getComments().size();
        this.isLiked = isLiked;
    }
}
