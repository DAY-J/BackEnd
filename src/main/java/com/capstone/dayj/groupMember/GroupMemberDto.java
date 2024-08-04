package com.capstone.dayj.groupMember;

import com.capstone.dayj.appUser.AppUser;
import com.capstone.dayj.friendGroup.FriendGroup;
import com.capstone.dayj.plan.PlanDto;
import com.capstone.dayj.statistics.StatisticsDto;
import lombok.*;

import java.time.LocalDate;
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
        private StatisticsDto.Response achievementRate;
        private List<PlanDto.groupResponse> groupMemberPlan;

        public Response(GroupMember groupMember, int mode) {
            this.id = groupMember.getId();
            this.appUserId = groupMember.getAppUser().getId();
            this.nickname = groupMember.getAppUser().getNickname();
            if (mode == 1) { // 달성률 목록 보여줄 때
                this.achievementRate = groupMember.getAppUser().getStatistics().stream()
                        .filter(statistics -> statistics.getDate().isEqual(LocalDate.now())).map(StatisticsDto.Response::new)
                        .findFirst().orElse(null);
            } else if (mode == 2) { // 친구 plan 보여줄 때
                this.groupMemberPlan = groupMember.getAppUser().getPlans().stream()
                        .filter(plan -> plan.getIsPublic() && plan.getPlanOption().getPlanStartTime().toLocalDate().isEqual(LocalDate.now()))
                        .map(PlanDto.groupResponse::new).collect(Collectors.toList());
            }
        }
    }
}
