package com.capstone.dayj.comment;

import com.capstone.dayj.appUser.AppUser;
import com.capstone.dayj.post.Post;
import lombok.*;

import java.time.LocalDateTime;

public class CommentDto {
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {
        private int id;
        private int parentId;
        private String content;
        private Boolean isAnonymous;
        private AppUser appUser;
        private Post post;
        
        public Comment toEntity() {
            return Comment.builder()
                    .id(id)
                    .parentId(parentId)
                    .content(content)
                    .isAnonymous(isAnonymous)
                    .appUser(appUser)
                    .post(post)
                    .build();
        }
    }
    
    @Getter
    public static class Response {
        private final int id;
        private final int parentId;
        private final String content;
        private final Boolean isAnonymous;
        private final String author;
        private final LocalDateTime createdAt;
        private final LocalDateTime updatedAt;
        
        public Response(Comment comment) {
            this.id = comment.getId();
            this.parentId = comment.getParentId();
            this.content = comment.getContent();
            this.isAnonymous = comment.getIsAnonymous();
            this.author = comment.getAppUser().getNickname();
            this.createdAt = comment.getCreatedAt();
            this.updatedAt = comment.getUpdatedAt();
        }
    }
}
