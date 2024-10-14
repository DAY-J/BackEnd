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
    @Builder
    public static class Request {
        private AppUser appUser;
        private FriendGroup friendGroup;

        public GroupMember toEntity() {
            return GroupMember.builder()
                    .appUser(appUser)
                    .friendGroup(friendGroup)
                    .build();
        }
    }

    @Getter
    public static class Response {
        private final int appUserId;
        private final String nickname;
        private final StatisticsDto.Response achievementRate;
        private final List<PlanDto.groupResponse> groupMemberPlans;

        public Response(GroupMember groupMember) {
            this.appUserId = groupMember.getAppUser().getId();
            this.nickname = groupMember.getAppUser().getNickname();
            this.achievementRate = groupMember.getAppUser().getStatistics().stream()
                    .filter(statistics -> statistics.getDate().isEqual(LocalDate.now())).map(StatisticsDto.Response::new)
                    .findFirst().orElse(null);
            this.groupMemberPlans = groupMember.getAppUser().getPlans().stream()
                    .filter(plan -> plan.getIsPublic() && plan.getPlanOption().getPlanStartTime().toLocalDate().isEqual(LocalDate.now()))
                    .map(PlanDto.groupResponse::new).collect(Collectors.toList());
        }
    }
}
