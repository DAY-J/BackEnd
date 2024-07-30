package com.capstone.dayj.post;

import com.capstone.dayj.appUser.AppUser;
import com.capstone.dayj.appUser.AppUserRepository;
import com.capstone.dayj.exception.CustomException;
import com.capstone.dayj.exception.ErrorCode;
import com.capstone.dayj.tag.Tag;
import com.capstone.dayj.util.ImageUploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final AppUserRepository appUserRepository;
    private final ImageUploader imageUploader;
    
    @Transactional
    public PostDto.Response createPost(int app_user_id, PostDto.Request dto, List<MultipartFile> images) throws CustomException, IOException {
        if (images != null && !images.isEmpty()) {
            List<String> postPhotos = new ArrayList<>();
            for (MultipartFile image : images) {
                String uploadedImageUrl = imageUploader.upload(image);
                postPhotos.add(uploadedImageUrl);
            }
            dto.setPostPhoto(postPhotos);
        }
        
        AppUser appUser = appUserRepository.findById(app_user_id)
                .orElseThrow(() -> new CustomException(ErrorCode.APP_USER_NOT_FOUND));
        dto.setAppUser(appUser);
        Post savedPost = postRepository.save(dto.toEntity());
        return new PostDto.Response(savedPost);
    }
    
    @Transactional(readOnly = true)
    public List<PostDto.Response> readAllPost() {
        List<Post> posts = postRepository.findAll();
        
        if (posts.isEmpty())
            throw new CustomException(ErrorCode.POST_NOT_FOUND);
        
        return posts.stream().map(PostDto.Response::new).collect(Collectors.toList());
    }
    
    @Transactional
    public PostDto.Response readPostById(int id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        
        postRepository.incrementPostView(id);
        
        return new PostDto.Response(post);
    }
    
    @Transactional(readOnly = true)
    public List<PostDto.Response> readPostByTag(Tag tag) {
        List<Post> posts = postRepository.findByPostTag(tag);
        
        if (posts.isEmpty())
            throw new CustomException(ErrorCode.POST_NOT_FOUND);
        
        return posts.stream().map(PostDto.Response::new).collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<PostDto.Response> searchPostsByKeyword(String keyword) {
        List<Post> posts = postRepository.findByPostTitleContainingOrPostContentContaining(keyword, keyword);
        
        if (posts.isEmpty())
            throw new CustomException(ErrorCode.POST_NOT_FOUND);
        
        return posts.stream().map(PostDto.Response::new).collect(Collectors.toList());
    }
    
    @Transactional
    public void deletePostById(int postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        
        postRepository.deleteById(post.getId());
    }
    
    @Transactional
    public void likePost(int postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        
        postRepository.incrementPostLike(postId);
    }
    
    @Transactional
    public PostDto.Response updatePost(int postId, PostDto.Request dto, List<MultipartFile> images) throws IOException {
        // 이미지 지우고 싶을 때는 images 자체를 보내지 않으면 됨.
        if (images != null && !images.isEmpty()) {
            List<String> postPhotos = new ArrayList<>();
            for (MultipartFile image : images) {
                String uploadedImageUrl = imageUploader.upload(image);
                postPhotos.add(uploadedImageUrl);
            }
            dto.setPostPhoto(postPhotos);
        }
        
        Post findPost = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        findPost.update(dto);
        return new PostDto.Response(findPost);
    }
}