package com.capstone.dayj.appUser;

import com.capstone.dayj.comment.Comment;
import com.capstone.dayj.common.BaseEntity;
import com.capstone.dayj.groupMember.GroupMember;
import com.capstone.dayj.plan.Plan;
import com.capstone.dayj.post.Post;
import com.capstone.dayj.statistics.Statistics;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true, exclude = {"groupMembers", "plans", "posts", "comments", "statistics"})
public class AppUser extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; //기본키
    @Email
    private String username; //유저 구글 이메일
    private String password; //유저 비밀번호
    private String role; //유저 권한
    @Column(nullable = false, unique = true)
    private String nickname; // 유저 닉네임, 중복 불가, 친구 그룹 추가에 사용
    //    private String profilePhoto;
    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean isAlarm;
    
    @OneToMany(mappedBy = "appUser", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<GroupMember> groupMembers;
    
    @OneToMany(mappedBy = "appUser", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Plan> plans;
    
    @OneToMany(mappedBy = "appUser", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Post> posts;
    
    @OneToMany(mappedBy = "appUser", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Comment> comments;
    
    @OneToMany(mappedBy = "appUser", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    private List<Statistics> statistics;
    
    public void update(AppUserDto.Request dto) {
        this.nickname = (dto.getNickname() == null ? this.nickname : dto.getNickname());
        this.isAlarm = (dto.getIsAlarm() == null ? this.isAlarm : dto.getIsAlarm());
//        this.profilePhoto = (dto.getProfilePhoto() == null ? this.profilePhoto : dto.getProfilePhoto());
    }
    
    @Builder
    public AppUser(int id, String nickname, String password, String username, String role, Boolean isAlarm, List<GroupMember> groupMembers, List<Plan> plans, List<Post> posts, List<Comment> comments, List<Statistics> statistics) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.nickname = nickname;
//        this.profilePhoto = profilePhoto;
        this.isAlarm = isAlarm;
        this.groupMembers = groupMembers;
        this.plans = plans;
        this.posts = posts;
        this.comments = comments;
        this.statistics = statistics;
    }
}

