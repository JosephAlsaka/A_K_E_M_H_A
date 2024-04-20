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





