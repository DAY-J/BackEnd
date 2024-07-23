package com.capstone.dayj.friendGroup;

import com.capstone.dayj.common.BaseEntity;
import com.capstone.dayj.groupMember.GroupMember;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class FriendGroup extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @OneToMany(mappedBy = "friendGroup", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<GroupMember> groupMember;
    
    @Column
    private String groupGoal;
    
    @Column(nullable = false)
    private String groupName;
    
    public void updateGroupName(FriendGroupDto.Request dto) {
        this.groupName = dto.getGroupName();
    }
    public void updateGroupGoal(FriendGroupDto.Request dto){
        this.groupGoal = dto.getGroupGoal();
    }
    
    @Builder
    public FriendGroup(int id, List<GroupMember> groupMember, String groupGoal, String groupName) {
        this.id = id;
        this.groupGoal = groupGoal;
        this.groupName = groupName;
        this.groupMember = groupMember;
    }
}
