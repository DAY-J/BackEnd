package com.capstone.dayj.groupMember;

import com.capstone.dayj.appUser.AppUser;
import com.capstone.dayj.friendGroup.FriendGroup;
import com.capstone.dayj.plan.Plan;
import com.capstone.dayj.plan.PlanDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
        private final int appUserId;
        private final String nickname;
        private final int achievementRate;
        private final List<PlanDto.groupResponse> groupMemberPlan;

        public Response(GroupMember groupMember) {
            this.id = groupMember.getId();
            this.appUserId = groupMember.getAppUser().getId();
            this.nickname = groupMember.getAppUser().getNickname();
            this.achievementRate = 99; // 통계 구현되면 수정 필요
            this.groupMemberPlan = groupMember.getAppUser().getPlans().stream()
                    .filter(plan -> plan.isPublic() && plan.getPlanOption().getPlanStartTime().toLocalDate().isEqual(LocalDate.now()))
                    .map(PlanDto.groupResponse::new).collect(Collectors.toList());
        }
    }
}
