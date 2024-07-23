package com.capstone.dayj.groupMember;

import com.capstone.dayj.appUser.AppUser;
import com.capstone.dayj.friendGroup.FriendGroup;
import com.capstone.dayj.plan.Plan;
import com.capstone.dayj.plan.PlanDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

public class GroupMemberDto {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {
        private int id;
        private AppUser appUser;
        private FriendGroup friendGroup;
        
        public GroupMember toEntity() {
            return GroupMember.builder()
                    .id(id)
                    .appUser(appUser)
                    .friendGroup(friendGroup)
                    .build();
        }
    }
    
    @Getter
    public static class Response {
        private final int id;
        private final String nickname;
        private final List<PlanDto.Response> groupMemberPlan;
        
        public Response(GroupMember groupMember) {
            this.id = groupMember.getId();
            this.nickname = groupMember.getAppUser().getNickname();
            this.groupMemberPlan = groupMember.getAppUser().getPlans().stream()
                    .filter(Plan::isPublic).map(PlanDto.Response::new).collect(Collectors.toList());
        }
    }
}
