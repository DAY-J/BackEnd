package com.capstone.dayj.appUser;

import com.capstone.dayj.appUserFriendGroup.AppUserFriendGroup;
import com.capstone.dayj.comment.Comment;
import com.capstone.dayj.common.BaseEntity;
import com.capstone.dayj.plan.Plan;
import com.capstone.dayj.post.Post;
import com.capstone.dayj.setting.AlarmSetting;
import com.capstone.dayj.setting.Setting;
import com.capstone.dayj.setting.SettingDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;

import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true, exclude = {"appUserFriendGroup", "plans", "posts", "comments", "setting"})
public class AppUser extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; //기본키
    private String name; //유저 이름 (provider + providerId)
    @Column(nullable = false)
    private String nickname; // 유저 닉네임, 중복 불가, 친구 그룹 추가에 사용
    private String password; //유저 비밀번호
    private String email; //유저 구글 이메일
    private String role; //유저 권한
    private String provider; //공급자
    private String providerId; //공급 아이디
    
    //유저 닉네임 설정하는 기능 구현 필요
    
    @OneToMany(mappedBy = "appUser")
    @JsonIgnore
    private List<AppUserFriendGroup> appUserFriendGroup;
    
    @OneToMany(mappedBy = "appUser")
    private List<Plan> plans;
    
    @OneToMany(mappedBy = "appUser")
    private List<Post> posts;
    
    @OneToMany(mappedBy = "appUser")
    private List<Comment> comments;
    
    @OneToOne(mappedBy = "appUser")
    private Setting setting;
    
    public void update(String nickname) {
        this.nickname = nickname;
    }
    
    @Transactional
    @PrePersist
    public void prePersist() {
    
    }
    
    @Transactional
    @PostPersist
    public void postPersist() {
        SettingDto.Request setting = SettingDto.Request.builder()
                .alarmSettings(List.of(AlarmSetting.ALL, AlarmSetting.PLAN, AlarmSetting.FRIENDGROUP, AlarmSetting.POST, AlarmSetting.APP))
                .appUser(this)
                .build();
    }
    
    @Builder
    public AppUser(int id, String name, String nickname, String password, String email, String role, String provider, String providerId, List<AppUserFriendGroup> appUserFriendGroup, List<Plan> plans, List<Post> posts, List<Comment> comments, Setting setting) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
        this.appUserFriendGroup = appUserFriendGroup;
        this.plans = plans;
        this.posts = posts;
        this.comments = comments;
        this.setting = setting;
    }
}

