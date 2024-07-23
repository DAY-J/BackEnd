package com.capstone.dayj.friendGroup;

import com.capstone.dayj.groupMember.GroupMember;
import com.capstone.dayj.groupMember.GroupMemberDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class FriendGroupDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {
        private int id;
        private String groupGoal;
        private String groupName;
        private List<GroupMember> groupMembers;

        public FriendGroup toEntity(){
            return FriendGroup.builder()
                    .id(id)
                    .groupGoal(groupGoal)
                    .groupName(groupName)
                    .groupMember(groupMembers)
                    .build();
        }
    }

    @Getter
    public static class Response {
        private final int id;
        private final String groupGoal;
        private final String groupName;
        private final LocalDateTime createdAt;
        private final List<GroupMemberDto.Response> groupMember;
        
        public Response(FriendGroup friendGroup){
            this.id = friendGroup.getId();
            this.groupGoal = friendGroup.getGroupGoal();
            this.groupName = friendGroup.getGroupName();
            this.createdAt = friendGroup.getCreatedAt();
            this.groupMember = friendGroup.getGroupMember().stream().map(GroupMemberDto.Response::new).collect(Collectors.toList());

        }

    }
}

