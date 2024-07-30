package com.capstone.dayj.post;

import com.capstone.dayj.tag.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {
    PostService postService;
    
    public PostController(PostService postService) {
        this.postService = postService;
    }
    
    @PostMapping("/app-user/{app_user_id}")
    public PostDto.Response createPost(
            @PathVariable int app_user_id,
            @RequestPart(value = "dto") PostDto.Request dto,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) throws IOException {
        return postService.createPost(app_user_id, dto, images);
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
    public List<PostDto.Response> readPostByTag(@PathVariable Tag post_tag) {
        return postService.readPostByTag(post_tag);
    }
    
    @GetMapping("search/{keyword}")
    public List<PostDto.Response> readPostByKeyword(@PathVariable String keyword) {
        return postService.searchPostsByKeyword(keyword);
    }
    
    @PatchMapping("/{post_id}")
    public PostDto.Response patchPost(
            @PathVariable int post_id,
            @RequestPart(value = "dto") PostDto.Request dto,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) throws IOException {
        return postService.updatePost(post_id, dto, images);
    }
    
    @PatchMapping("like/{post_id}")
    public void likePost(@PathVariable int post_id) {
        postService.likePost(post_id);
    }
    
    @DeleteMapping("/{post_id}")
    public void deletePostById(@PathVariable int post_id) {
        postService.deletePostById(post_id);
    }
    
}
