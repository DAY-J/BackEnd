package com.capstone.dayj.comment;

import com.capstone.dayj.appUser.AppUser;
import com.capstone.dayj.common.BaseEntity;
import com.capstone.dayj.post.Post;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@ToString(callSuper = true, exclude = {"appUser", "post"})
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(nullable = false)
    private String content;
    
    
    @Column(nullable = false)
    @ColumnDefault("1")
    private boolean commentIsAnonymous;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id", referencedColumnName = "id")
    private AppUser appUser;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Post post;
    
    public void update(String content, boolean commentIsAnonymous) {
        this.content = content;
        this.commentIsAnonymous = commentIsAnonymous;
    }
    
    @Builder
    public Comment(int id, String content, LocalDateTime commentCreateDate, LocalDateTime commentUpdateDate, boolean commentIsAnonymous, AppUser appUser, Post post) {
        this.id = id;
        this.content = content;
        this.commentIsAnonymous = commentIsAnonymous;
        this.appUser = appUser;
        this.post = post;
    }
}
