package com.grad.akemha.controller;

import com.grad.akemha.dto.BaseResponse;
import com.grad.akemha.dto.comment.CommentResponse;
import com.grad.akemha.entity.Comment;
import com.grad.akemha.exception.NotFoundException;
import com.grad.akemha.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    // Read
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<CommentResponse>> getCommentById(
            @PathVariable int id) {
        try {
            CommentResponse response = commentService.getCommentById(id);

            return ResponseEntity.ok().body(new BaseResponse<>
                    (HttpStatus.OK.value(), "Comment Found successfully", response));

        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>
                    (HttpStatus.NOT_FOUND.value(), e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse<>
                    (HttpStatus.BAD_REQUEST.value(), "Something went wrong", null));
        }
    }

    @GetMapping("/all/{postId}")
    public ResponseEntity<BaseResponse<List<CommentResponse>>> getAllComments(@PathVariable int postId) {
        try {
            List<Comment> comments = commentService.getAllComments(postId);
            List<CommentResponse> response = comments.stream().map(CommentResponse::new).toList();

            return ResponseEntity.ok().body(new BaseResponse<>
                    (HttpStatus.OK.value(), "All Comments", response));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse<>
                    (HttpStatus.BAD_REQUEST.value(), "Something went wrong", null));
        }
    }

    //    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping("/{postId}")
    public ResponseEntity<BaseResponse<CommentResponse>> addPost(@PathVariable int postId,
                                                                 @RequestParam String text,
                                                                 @RequestHeader HttpHeaders httpHeaders
    ) {
        try {
            CommentResponse response = commentService.createComment(postId, text, httpHeaders);
            return ResponseEntity.ok().body(new BaseResponse<>
                    (HttpStatus.CREATED.value(), "Comment created successfully", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse<>
                    (HttpStatus.BAD_REQUEST.value(), "Something went wrong", null));
        }

    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<CommentResponse>> updateComment(
            @PathVariable int id, @RequestParam String description) {
        CommentResponse response = commentService.updateComment(id, description);
        try {
            return ResponseEntity.ok().body(new BaseResponse<>
                    (HttpStatus.CREATED.value(), "Comment updated successfully", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse<>
                    (HttpStatus.BAD_REQUEST.value(), "Something went wrong", null));
        }
    }


    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<CommentResponse>> deleteComment(
            @PathVariable int id) {
        CommentResponse response = commentService.deleteComment(id);
        try {
            return ResponseEntity.ok().body(new BaseResponse<>
                    (HttpStatus.OK.value(), "Comment deleted successfully", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse<>
                    (HttpStatus.BAD_REQUEST.value(), "Something went wrong", null));
        }
    }


}
