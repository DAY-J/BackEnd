package com.capstone.dayj.post;

import com.capstone.dayj.appUser.AppUser;
import com.capstone.dayj.comment.CommentDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

public class PostDto {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {
        private int id;
        private int postView;
        private int postLike;
        private String postTitle;
        private String postContent;
        private String postTag;
        private boolean postIsAnonymous;
        private String postPhoto;
        private AppUser appUser;
        
        public Post toEntity() {
            return Post.builder()
                    .id(id)
                    .postView(postView)
                    .postLike(postLike)
                    .postTitle(postTitle)
                    .postContent(postContent)
                    .postTag(postTag)
                    .postIsAnonymous(postIsAnonymous)
                    .postPhoto(postPhoto)
                    .appUser(appUser)
                    .build();
        }
    }
    
    @Getter
    public static class Response {
        private final int id;
        private final int postView;
        private final int postLike;
        private final String postTitle;
        private final String postContent;
        private final String postTag;
        private final boolean postIsAnonymous;
        private final String postPhoto;
        @JsonIgnore
        private final AppUser appUser;
        @JsonIgnore
        private final List<CommentDto.Response> comment;
        
        /* Entity -> Dto */
        public Response(Post post) {
            this.id = post.getId();
            this.postView = post.getPostView();
            this.postLike = post.getPostLike();
            this.postTitle = post.getPostTitle();
            this.postContent = post.getPostContent();
            this.postTag = post.getPostTag();
            this.postIsAnonymous = post.isPostIsAnonymous();
            this.postPhoto = post.getPostPhoto();
            this.appUser = post.getAppUser();
            this.comment = post.getComment().stream().map(CommentDto.Response::new).collect(Collectors.toList());
        }
    }
}
