package com.capstone.dayj.friendGroup;

import com.capstone.dayj.groupMember.GroupMember;
import com.capstone.dayj.groupMember.GroupMemberDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class FriendGroupDto {
    @Data
    @Builder
    public static class Request {
        private String groupGoal;
        private String groupName;
        private List<GroupMember> groupMembers;

        public FriendGroup toEntity() {
            return FriendGroup.builder()
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
        private final List<GroupMemberDto.Response> groupMemberList; // 닉네임, 달성률, 당일 계획

        public Response(FriendGroup friendGroup, int app_user_id) {
            this.id = friendGroup.getId();
            this.groupGoal = friendGroup.getGroupGoal();
            this.groupName = friendGroup.getGroupName();
            this.createdAt = friendGroup.getCreatedAt();
            this.groupMemberList = friendGroup.getGroupMember().stream()
                    .map(GroupMemberDto.Response::new)
                    .collect(Collectors.toList());}
    }
}

