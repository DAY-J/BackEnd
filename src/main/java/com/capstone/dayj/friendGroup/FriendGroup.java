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
@ToString(callSuper = true, exclude = {"groupMember"})
public class FriendGroup extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @OneToMany(mappedBy = "friendGroup", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    @JsonIgnore
    private List<GroupMember> groupMember;
    
    @Column
    private String groupGoal;
    
    @Column(nullable = false)
    private String groupName;

    @Column(nullable = false)
    @ColumnDefault("0")
    private boolean groupExit;
    
    public void update(String groupName) {
        this.groupName = groupName;
    }

    public void updateGroupGoal(String groupGoal){
        this.groupGoal = groupGoal;
    }
    
    @Builder
    public FriendGroup(int id, List<GroupMember> groupMember, String groupGoal, String groupName, boolean groupExit) {
        this.id = id;
        this.groupGoal = groupGoal;
        this.groupName = groupName;
        this.groupExit = groupExit;
        this.groupMember = groupMember;
    }
}
