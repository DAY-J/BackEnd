package com.capstone.dayj.post;

import com.capstone.dayj.appUser.AppUser;
import com.capstone.dayj.comment.CommentDto;
import com.capstone.dayj.tag.Tag;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class PostDto {
    @Data
    @Builder
    public static class Request {
        private String postTitle;
        private String postContent;
        private Tag postTag;
        private Boolean isAnonymous;
        private List<String> postPhoto;
        private AppUser appUser;
        
        public Post toEntity() {
            return Post.builder()
                    .postTitle(postTitle)
                    .postContent(postContent)
                    .postTag(postTag)
                    .isAnonymous(isAnonymous)
                    .postPhoto(postPhoto)
                    .appUser(appUser)
                    .build();
        }
    }
    
    @Getter
    public static class Response {
        private final int id;
        private final int appUserId;
        private final int postView;
        private final int postLike;
        private final String postTitle;
        private final String postContent;
        private final Tag postTag;
        private final Boolean isAnonymous;
        private final List<String> postPhoto;
        private final LocalDateTime createdAt;
        private final LocalDateTime updatedAt;
        private final String author;
        private final List<CommentDto.Response> comment;
        
        /* Entity -> Dto */
        public Response(Post post) {
            this.id = post.getId();
            this.appUserId = post.getAppUser().getId();
            this.postView = post.getPostView();
            this.postLike = post.getPostLike();
            this.postTitle = post.getPostTitle();
            this.postContent = post.getPostContent();
            this.postTag = post.getPostTag();
            this.isAnonymous = post.getIsAnonymous();
            this.postPhoto = post.getPostPhoto();
            this.createdAt = post.getCreatedAt();
            this.updatedAt = post.getUpdatedAt();
            this.author = post.getAppUser().getNickname();
            this.comment = (post.getComment() == null ?
                    null : post.getComment().stream()
                    .map(CommentDto.Response::new).collect(Collectors.toList()));
        }
    }
}
