package com.capstone.dayj.post;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {
    PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/app-user/{app_user_id}")
    public void createPost(@PathVariable int app_user_id, @Valid @RequestBody PostDto.Request dto) {
        postService.createPost(dto, app_user_id);
    }

    @GetMapping
    public List<PostDto.Response> readAllPost() {
        return postService.readAllPost();
    }

    @GetMapping("/{post_id}")
    public PostDto.Response readPostById(@PathVariable int post_id) {
        return postService.readPostById(post_id);
    }

    @GetMapping("tag/{post_tag}")
    public List<PostDto.Response> readPostByTag(@PathVariable String post_tag){
        return postService.readPostByTag(post_tag);
    }

    @GetMapping("search/{keyword}")
    public List<PostDto.Response> readPostByKeyword(@PathVariable String keyword){
        return postService.searchPostsByKeyword(keyword);
    }

    @PatchMapping("/{post_id}")
    public void patchPost(@PathVariable int post_id, @Valid @RequestBody PostDto.Request post) {
        postService.updatePost(post_id, post);
    }

    @PatchMapping("like/{post_id}")
    public void likePost (@PathVariable int post_id) {
        postService.likePost(post_id);
    }

    @DeleteMapping("/{post_id}")
    public void deletePostById(@PathVariable int post_id) {
        postService.deletePostById(post_id);
    }

}
