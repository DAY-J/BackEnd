package com.capstone.dayj.comment;


import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
public class CommentController {

    CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{post_id}/app-user/{app_user_id}/comment")
    public void createComment(@PathVariable int post_id, @PathVariable int app_user_id, @Valid @RequestBody CommentDto.Request dto){
        commentService.createComment(post_id, app_user_id, dto);
    }

    @GetMapping("/{post_id}/comment")
    public List<CommentDto.Response> readAllComment(@PathVariable int post_id){
        return commentService.readAllComment(post_id);
    }

    @GetMapping("/{post_id}/comment/{comment_id}")
    public CommentDto.Response readCommentById(@PathVariable int post_id, @PathVariable int comment_id){
        return commentService.readCommentById(post_id, comment_id);
    }

    @PatchMapping("/{post_id}/comment/{comment_id}")
    public void patchComment(@PathVariable int post_id, @PathVariable int comment_id, @Valid @RequestBody CommentDto.Request dto){
        commentService.patchComment(post_id, comment_id, dto);
    }

    @DeleteMapping("/{post_id}/comment/{comment_id}")
    public void deleteCommentById(@PathVariable int post_id, @PathVariable int comment_id){
        commentService.deleteCommentById(post_id, comment_id);
    }
}
