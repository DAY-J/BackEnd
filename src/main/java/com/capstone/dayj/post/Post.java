package com.capstone.dayj.post;

import com.capstone.dayj.appUser.AppUser;
import com.capstone.dayj.comment.Comment;
import com.capstone.dayj.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true, exclude = {"comment", "appUser"})
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(nullable = false)
    @ColumnDefault("0")
    private int postView;
    @Column(nullable = false)
    @ColumnDefault("0")
    private int postLike;
    
    @Column(nullable = false)
    private String postTitle;
    @Column(nullable = false)
    private String postContent;
    private String postTag;
    
    @Column(nullable = false)
    @ColumnDefault("1")
    private boolean postIsAnonymous;
    
    @Column
    private String postPhoto;
    
    @OneToMany(mappedBy = "post")
    private List<Comment> comment;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id", referencedColumnName = "id")
    private AppUser appUser;
    
    public void update(String postTitle, String postContent, String postTag, boolean postIsAnonymous, String postPhoto) {
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.postTag = postTag;
        this.postIsAnonymous = postIsAnonymous;
        this.postPhoto = postPhoto;
    }

    public void setPostView(int postView){
        this.postView = postView;
    }

    public void setPostLike(int postLike){
        this.postLike = postLike;
    }
    
    @Builder
    public Post(int id, int postView, int postLike, String postTitle, String postContent, String postTag, LocalDateTime postCreateDate, LocalDateTime postUpdateDate, boolean postIsAnonymous, String postPhoto, List<Comment> comment, AppUser appUser) {
        this.id = id;
        this.postView = postView;
        this.postLike = postLike;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.postTag = postTag;
        this.postIsAnonymous = postIsAnonymous;
        this.postPhoto = postPhoto;
        this.comment = comment;
        this.appUser = appUser;
    }
}

