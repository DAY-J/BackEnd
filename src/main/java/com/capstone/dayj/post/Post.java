package com.capstone.dayj.post;

import com.capstone.dayj.appUser.AppUser;
import com.capstone.dayj.comment.Comment;
import com.capstone.dayj.common.BaseEntity;
import com.capstone.dayj.tag.Tag;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true, exclude = {"appUser"})
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
    private Tag postTag;
    
    @Column(nullable = false)
    @ColumnDefault("1")
    private Boolean isAnonymous;
    
    @ElementCollection
    @CollectionTable(name = "post_photo", joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"))
    private List<String> postPhoto;
    
    @OneToMany(mappedBy = "post", cascade = {CascadeType.REMOVE})
    @JsonManagedReference
    private List<Comment> comment;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id", referencedColumnName = "id")
    @JsonIgnore
    private AppUser appUser;
    
    public void update(PostDto.Request dto) {
        this.postTitle = (dto.getPostTitle() == null ? this.postTitle : dto.getPostTitle());
        this.postContent = (dto.getPostContent() == null ? this.postContent : dto.getPostContent());
        this.postTag = (dto.getPostTag() == null ? this.postTag : dto.getPostTag());
        this.isAnonymous = (dto.getIsAnonymous() == null ? this.isAnonymous : dto.getIsAnonymous());
        this.postPhoto = dto.getPostPhoto();
    }
    
    @Builder
    public Post(int id, int postView, int postLike, String postTitle, String postContent, Tag postTag, Boolean isAnonymous, List<String> postPhoto, List<Comment> comment, AppUser appUser) {
        this.id = id;
        this.postView = postView;
        this.postLike = postLike;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.postTag = postTag;
        this.isAnonymous = isAnonymous;
        this.postPhoto = postPhoto;
        this.comment = comment;
        this.appUser = appUser;
    }
}

