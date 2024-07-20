package com.capstone.dayj.comment;


import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post/{post_id}")
public class CommentController {
    
    CommentService commentService;
    
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    
    @PostMapping("/app-user/{app_user_id}/comment")
    public void createComment(@PathVariable int post_id, @PathVariable int app_user_id, @Valid @RequestBody CommentDto.Request dto) {
        commentService.createComment(post_id, app_user_id, dto);
    }
    
    @PostMapping("/app-user/{app_user_id}/comment/{comment_id}")
    public void createReply(@PathVariable int post_id, @PathVariable int app_user_id, @PathVariable int comment_id, @Valid @RequestBody CommentDto.Request dto) {
        commentService.createReply(post_id, app_user_id, comment_id, dto);
    }
    
    @GetMapping("comment/{comment_id}/reply")
    public List<CommentDto.Response> readAllReplyByCommentId(@PathVariable int post_id, @PathVariable int comment_id) {
        return commentService.readAllReplyByCommentId(post_id, comment_id);
    }
    
    @GetMapping("/comment")
    public List<CommentDto.Response> readAllComment(@PathVariable int post_id) {
        return commentService.readAllComment(post_id);
    }
    
    @GetMapping("/comment/{comment_id}")
    public CommentDto.Response readCommentById(@PathVariable int post_id, @PathVariable int comment_id) {
        return commentService.readCommentById(post_id, comment_id);
    }
    
    @PatchMapping("/comment/{comment_id}")
    public void patchComment(@PathVariable int post_id, @PathVariable int comment_id, @Valid @RequestBody CommentDto.Request dto) {
        commentService.patchComment(post_id, comment_id, dto);
    }
    
    @DeleteMapping("/comment/{comment_id}")
    public void deleteCommentById(@PathVariable int post_id, @PathVariable int comment_id) {
        commentService.deleteCommentById(post_id, comment_id);
    }
}
