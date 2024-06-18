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


// FIXME for scheduling
// for scheduling:
//Every 5 Minutes:
//        "0 */5 * * * *": This expression means the task will run every 5 minutes. The */5 in the minutes field specifies that the task should run every 5 minutes.
//Every Hour:
//        "0 0 * * * *": This expression means the task will run at the beginning of every hour. The 0 in the minutes and seconds fields ensures it starts at the exact beginning of each hour.
//Every Day at Midnight:
//        "0 0 0 * * *": This expression means the task will run at midnight every day. The three 0 values in the hours, minutes, and seconds fields ensure it runs precisely at midnight.
//Every Sunday at Midnight:
//        "0 0 0 * * SUN": This expression means the task will run at midnight every Sunday. The SUN in the day-of-week field specifies Sunday.
//Every Weekday at Noon:
//        "0 0 12 ? * MON-FRI": This expression means the task will run at noon (12:00 PM) every weekday (Monday to Friday). The MON-FRI in the day-of-week field specifies weekdays.
//Every Last Day of the Month at Midnight:
//        "0 0 0 L * ?": This expression means the task will run at midnight on the last day of every month. The L in the day-of-month field represents the last day of the month.
//Every 3 hours:
//         0 0 */3 * * *
//NOTE:
//     Seconds (*):
//     Indicates every second of the minute (0-59).
//     Minutes (*):
//     Indicates every minute of the hour (0-59).
//     Hours (*):
//     Indicates every hour of the day (0-23).
//     Day of Month (*):
//     Indicates every day of the month (1-31).
//     Month (*):
//     Indicates every month of the year (1-12 or JAN-DEC).
//     Day of Week (*):
//     Indicates every day of the week (0-7 or SUN-SAT, where 0 and 7 represent Sunday).




//FIXME this was the register function in AuthService
/*
    public AuthResponse register(RegisterRequest request)
//            throws RegistrationException
    {
        if (userAlreadyExists(request.getEmail())) {
            throw new EmailAlreadyExistsException("User already exists");
        }

        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .dob(request.getDob())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .isActive(true)
                .build();

        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .userEmail(user.getEmail())
                .build();
    }

    private boolean userAlreadyExists(String email) {
        return userRepository.existsByEmail(email);
    }
*/

// FIXME from AuthService in login function line:79
//another way
////        final AuthResponse authResponseModel;
////        authResponseModel = new AuthResponseModel(
////                jwtToken,
////                HttpStatus.OK.value(),
////                user.getEmail(),
////                "Successfully logged in"
////        );

















