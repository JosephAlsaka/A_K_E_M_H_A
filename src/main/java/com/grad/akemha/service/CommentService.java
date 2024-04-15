package com.grad.akemha.service;

import com.grad.akemha.dto.comment.CommentResponse;
import com.grad.akemha.dto.post.DoctorResponse;
import com.grad.akemha.entity.Comment;
import com.grad.akemha.entity.Post;
import com.grad.akemha.entity.User;
import com.grad.akemha.exception.NotFoundException;
import com.grad.akemha.repository.CommentRepository;
import com.grad.akemha.repository.PostRepository;
import com.grad.akemha.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final JwtService jwtService;

    // not necessary
    public CommentResponse getCommentById(int id) {
        Optional<Comment> optionalComment = commentRepository.findById((long) id);

        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            return CommentResponse
                    .builder()
                    .id(comment.getId())
                    .doctor(new DoctorResponse(comment.getUser()))
                    .description(comment.getText())
                    .build();
        } else {
            throw new NotFoundException("No comment in that Id: " + id);
        }
    }

    public List<Comment> getAllComments(int postId) {
        Optional<Post> optionalPost = postRepository.findById((long) postId);
        if (optionalPost.isPresent()) {
            return optionalPost.get().getComments();
        } else {
            throw new NotFoundException("Can't find post with the id of: " + postId);
        }
    }

    // Create
    public CommentResponse createComment(int postId,
                                         String text,
                                         HttpHeaders httpHeaders) {
        Optional<Post> optionalPost = postRepository.findById((long) postId);
        if (optionalPost.isPresent()) {
            User user = jwtService.extractUserFromToken(httpHeaders);
            Comment comment = new Comment();
            comment.setText(text);
            comment.setUser(user);
            comment.setPost(optionalPost.get());
            commentRepository.save(comment);
            return CommentResponse
                    .builder()
                    .id(comment.getId())
                    .doctor(new DoctorResponse(comment.getUser()))
                    .description(comment.getText())
                    .build();
        } else {
            throw new NotFoundException("Can't find post with the id of: " + postId);
        }
    }

    // Update
    public CommentResponse updateComment(int id, String description) {
        if (description == null) {
            throw new NotFoundException("No Data have been entered");
        }
        Optional<Comment> optionalComment = commentRepository.findById((long) id);
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            comment.setText(description);
            commentRepository.save(comment);
            return CommentResponse
                    .builder()
                    .id(comment.getId())
                    .doctor(new DoctorResponse(comment.getUser()))
                    .description(comment.getText())
                    .build();
        } else {
            throw new NotFoundException("No Comment with That id: " + id);
        }
    }

    // Delete
    public CommentResponse deleteComment(int id) {
        Optional<Comment> optionalComment = commentRepository.findById((long) id);

        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            commentRepository.deleteById((long) id);
            return CommentResponse
                    .builder()
                    .id(comment.getId())
                    .doctor(new DoctorResponse(comment.getUser()))
                    .description(comment.getText())
                    .build();
        } else {
            throw new NotFoundException("No Comment in that id: " + id);
        }
    }

}
