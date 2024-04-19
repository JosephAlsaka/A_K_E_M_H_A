package com.grad.akemha.dto.post;

import com.grad.akemha.dto.comment.CommentResponse;
import com.grad.akemha.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDetailsResponse {
    private Long id;
    private DoctorResponse doctor;
    private String imageUrl;
    private String description;
    private int likesCount;
    private int commentsCount;
    private List<CommentResponse> comments;

}
