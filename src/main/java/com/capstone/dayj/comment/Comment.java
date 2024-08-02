package com.capstone.dayj.comment;

import com.capstone.dayj.appUser.AppUser;
import com.capstone.dayj.common.BaseEntity;
import com.capstone.dayj.post.Post;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@ToString(callSuper = true, exclude = {"appUser"})
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int parentId;
    
    @Column(nullable = false)
    private String content;
    
    @Column(nullable = false)
    @ColumnDefault("1")
    private Boolean isAnonymous;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    @JsonBackReference
    private Post post;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id", referencedColumnName = "id")
    private AppUser appUser;
    
    public void update(CommentDto.Request dto) {
        this.content = (dto.getContent() == null ? this.content : dto.getContent());
        this.isAnonymous = (dto.getIsAnonymous() == null ? this.isAnonymous : dto.getIsAnonymous());
    }
    
    @Builder
    public Comment(int id, int parentId, String content, Boolean isAnonymous, AppUser appUser, Post post) {
        this.id = id;
        this.parentId = parentId;
        this.content = content;
        this.isAnonymous = isAnonymous;
        this.appUser = appUser;
        this.post = post;
    }
}
