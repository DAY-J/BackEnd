package com.capstone.dayj.friendGroup;

import com.capstone.dayj.groupMember.GroupMember;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class FriendGroupDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {
        private int id;
        private String groupGoal;
        private String groupName;
        private boolean groupExit;
        private List<GroupMember> groupMembers;

        public FriendGroup toEntity(){
            return FriendGroup.builder()
                    .id(id)
                    .groupGoal(groupGoal)
                    .groupName(groupName)
                    .groupExit(groupExit)
                    .groupMember(groupMembers)
                    .build();
        }
    }

    @Getter
    public static class Response {
        private final int id;
        private final String groupGoal;
        private final String groupName;
        private final boolean groupExit;
        private final LocalDateTime createdAt;
        @JsonIgnore
        private final List<GroupMember> groupMember;
        
        public Response(FriendGroup friendGroup){
            this.id = friendGroup.getId();
            this.groupGoal = friendGroup.getGroupGoal();
            this.groupName = friendGroup.getGroupName();
            this.groupExit = friendGroup.isGroupExit();
            this.groupMember = friendGroup.getGroupMember();
            this.createdAt = friendGroup.getCreatedAt();
        }

    }
}

