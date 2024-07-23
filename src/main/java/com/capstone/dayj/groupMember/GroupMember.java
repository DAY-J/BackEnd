package com.capstone.dayj.groupMember;

import com.capstone.dayj.appUser.AppUser;
import com.capstone.dayj.common.BaseEntity;
import com.capstone.dayj.friendGroup.FriendGroup;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true, exclude = {"appUser"})
public class GroupMember extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id", referencedColumnName = "id")
    private AppUser appUser;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_group_id", referencedColumnName = "id")
    @JsonBackReference
    private FriendGroup friendGroup;

    @Builder
    public GroupMember(int id, AppUser appUser, FriendGroup friendGroup) {
        this.id = id;
        this.appUser = appUser;
        this.friendGroup = friendGroup;
    }
}
