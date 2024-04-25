//package malnlazmeh;
//


// FIXME FROM POST CONTROLLER
// Read
//    @PreAuthorize("hasRole('USER') or hasRole('DOCTOR') or hasRole('OWNER')")
//    @GetMapping("/{id}")
//    public ResponseEntity<BaseResponse<PostDetailsResponse>> getPostById(
//            @PathVariable int id) {
//        PostDetailsResponse response = postService.getPostById(id);
//
//        return ResponseEntity.ok().body(new BaseResponse<>
//                (HttpStatus.OK.value(), "Post Found successfully", response));
//    }

// FIXME FROM POST SERVICE
// this method will be deleted because of pagination in comment
//    // Read
//    public PostDetailsResponse getPostById(int id) {
//        Optional<Post> optionalPost = postRepository.findById((long) id);
//
//        if (optionalPost.isPresent()) {
//            Post post = optionalPost.get();
//            List<Comment> comments = commentService.getAllComments(id);
//            List<CommentResponse> response = comments.stream().map(CommentResponse::new).toList();
//            return PostDetailsResponse
//                    .builder()
//                    .id(post.getId())
//                    .doctor(new DoctorResponse(post.getUser()))
//                    .imageUrl(post.getImageUrl())
//                    .description(post.getDescription())
//                    .likesCount(post.getLikes().size())
//                    .commentsCount(post.getComments().size())
//                    .comments(response)
//                    .build();
//        } else {
//            throw new NotFoundException("No Post in that Id: " + id);
//        }
//    }

// FIXME FROM COMMENT CONTROLLER

//    @PreAuthorize("hasRole('USER') or hasRole('DOCTOR') or hasRole('OWNER')")
// Read not necessary
//    @GetMapping("/{id}")
//    public ResponseEntity<BaseResponse<CommentResponse>> getCommentById(
//            @PathVariable int id) {
//        CommentResponse response = commentService.getCommentById(id);
//
//        return ResponseEntity.ok().body(new BaseResponse<>
//                (HttpStatus.OK.value(), "Comment Found successfully", response));
//    }

// also not necessary because when getting one post its comments also gets retrieved
//    @PreAuthorize("hasRole('USER') or hasRole('DOCTOR') or hasRole('OWNER')")
//    @GetMapping("/all/{postId}")
//    public ResponseEntity<BaseResponse<List<CommentResponse>>> getAllCommentsForOnePost(@PathVariable int postId) {
//      List<Comment> comments = commentService.getAllComments(postId);
//        List<CommentResponse> response = comments.stream().map(CommentResponse::new).toList();
//
//        return ResponseEntity.ok().body(new BaseResponse<>
//                (HttpStatus.OK.value(), "All Comments", response));
//    }

// FIXME FROM POST SERVICE RELATED TO LIKE
// this is working
//    // to match the post id and user id to make
//    // sure not adding more than one like by the same user
//    public boolean isLikeExists(Like like) {
//        Example<Like> likeExample = Example.of(like,
//                ExampleMatcher.matchingAll().withIgnorePaths("id"));
//        System.out.println(likeRepository.exists(likeExample));
//        return likeRepository.exists(likeExample);
//    }


/*
*
*  // add like in the old way (it's Working )
    public PostResponse addLike(int id, HttpHeaders httpHeaders) {
        Optional<Post> optionalPost = postRepository.findById((long) id);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            User user = jwtService.extractUserFromToken(httpHeaders);
            Like like = new Like();
            like.setPost(post);
            like.setUser(user);
            if (isLikeExists(like)) {
                throw new ForbiddenException("You can't add more than one like");
            } else {
                likeRepository.save(like);
            }

            return PostResponse
                    .builder()
                    .id(post.getId())
                    .doctor(new DoctorResponse(post.getUser()))
                    .imageUrl(post.getImageUrl())
                    .text(post.getText())
                    .likesCount(post.getLikes().size())
                    .commentsCount(post.getComments().size())
                    .build();
        } else {
            throw new NotFoundException("No Post in that id: " + id);
        }

    }

*
*
* */


// FIXME FROM MEDICAL RECORD REPOSITORY
// @Query("SELECT mr FROM MedicalRecord mr WHERE mr.user = :user ORDER BY mr.createTime DESC")
// Optional<MedicalRecord> findLastMedicalRecordByUser(@Param("user") User user, Pageable pageable);



