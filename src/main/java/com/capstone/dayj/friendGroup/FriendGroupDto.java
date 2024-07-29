package com.capstone.dayj.friendGroup;

import com.capstone.dayj.groupMember.GroupMember;
import com.capstone.dayj.groupMember.GroupMemberDto;
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
        private final List<GroupMemberDto.achievementRateResponse> achievementRates; // 달성률
        
        public Response(FriendGroup friendGroup, int app_user_id){
            this.id = friendGroup.getId();
            this.groupGoal = friendGroup.getGroupGoal();
            this.groupName = friendGroup.getGroupName();
            this.createdAt = friendGroup.getCreatedAt();
            this.groupMember = friendGroup.getGroupMember().stream()
                    .filter(groupMember -> groupMember.getAppUser().getId() != app_user_id)
                    .map(GroupMemberDto.Response::new).collect(Collectors.toList());
            this.achievementRates = friendGroup.getGroupMember().stream()
                    .map(GroupMemberDto.achievementRateResponse::new).collect(Collectors.toList());
        }
    }
}

