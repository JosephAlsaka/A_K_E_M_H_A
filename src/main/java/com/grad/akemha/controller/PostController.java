package com.grad.akemha.controller;

import com.grad.akemha.dto.BaseResponse;
import com.grad.akemha.dto.post.PostDetailsResponse;
import com.grad.akemha.dto.post.PostRequest;
import com.grad.akemha.dto.post.PostResponse;
import com.grad.akemha.entity.Post;
import com.grad.akemha.exception.ForbiddenException;
import com.grad.akemha.exception.NotFoundException;
import com.grad.akemha.service.PostService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    // Read
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<PostDetailsResponse>> getPostById(
            @PathVariable int id) throws Exception {
//        try {
        PostDetailsResponse response = postService.getPostById(id);

        return ResponseEntity.ok().body(new BaseResponse<>
                (HttpStatus.OK.value(), "Post Found successfully", response));

//        } catch (NotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>
//                    (HttpStatus.NOT_FOUND.value(), e.getMessage(), null));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse<>
//                    (HttpStatus.BAD_REQUEST.value(), "Something went wrong", null));
//        }
    }

    @GetMapping()
    public ResponseEntity<BaseResponse<List<PostResponse>>> getAllPosts() {
//        try {
        List<Post> posts = postService.getAllPosts();
        List<PostResponse> response = posts.stream().map(PostResponse::new).toList();

        return ResponseEntity.ok().body(new BaseResponse<>
                (HttpStatus.OK.value(), "All Posts", response));
//
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse<>
//                    (HttpStatus.BAD_REQUEST.value(), "Something went wrong", null));
//        }
    }

    //    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BaseResponse<PostResponse>> addPost(@ModelAttribute PostRequest postRequest,
                                                              @RequestHeader HttpHeaders httpHeaders
    ) {
//        try {
        PostResponse response = postService.createPost(postRequest, httpHeaders);
        return ResponseEntity.ok().body(new BaseResponse<>
                (HttpStatus.CREATED.value(), "Post created successfully", response));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse<>
//                    (HttpStatus.BAD_REQUEST.value(), "Something went wrong", null));
//        }

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<PostResponse>> deletePost(
            @PathVariable int id) {
//
//        try {
        PostResponse response = postService.deletePost(id);
        return ResponseEntity.ok().body(new BaseResponse<>
                (HttpStatus.OK.value(), "Post deleted successfully", response));
//        } catch (NotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>
//                    (HttpStatus.NOT_FOUND.value(), e.getMessage(), null));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse<>
//                    (HttpStatus.BAD_REQUEST.value(), "Something went wrong", null));
//        }
    }


    @PostMapping("/add_like/{postId}")
    public ResponseEntity<BaseResponse<PostResponse>> addLikeToPost(
            @PathVariable int postId,
            @RequestHeader HttpHeaders httpHeaders) {
//        try {
        PostResponse response = postService.addLike(postId, httpHeaders);
        return ResponseEntity.ok().body(new BaseResponse<>
                (HttpStatus.OK.value(), "Added like to the Post successfully", response));
//        } catch (ForbiddenException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse<>
//                    (HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
//        } catch (NotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>
//                    (HttpStatus.NOT_FOUND.value(), e.getMessage(), null));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse<>
//                    (HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something went wrong", null));
//        }
    }

    @DeleteMapping("/remove_like/{postId}")
    public ResponseEntity<BaseResponse<PostResponse>> removeLikeFromPost(
            @PathVariable int postId,
            @RequestHeader HttpHeaders httpHeaders) {
//        try {
        PostResponse response = postService.removeLike(postId, httpHeaders);
        return ResponseEntity.ok().body(new BaseResponse<>
                (HttpStatus.OK.value(), "Removed like from the Post successfully", response));
//        } catch (ForbiddenException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse<>
//                    (HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
//        } catch (NotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>
//                    (HttpStatus.NOT_FOUND.value(), e.getMessage(), null));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse<>
//                    (HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something went wrong", null));
//        }
    }

}


