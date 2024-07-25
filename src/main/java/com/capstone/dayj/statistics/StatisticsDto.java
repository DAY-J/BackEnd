package com.capstone.dayj.statistics;

import com.capstone.dayj.appUser.AppUser;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import java.time.LocalDate;

public class StatisticsDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        private int id;
        private String tag;
        private LocalDate startDate;
        private LocalDate endDate;
        private int totalGoals;
        private int achievedGoals;
        private double achievementPercentage;
        private AppUser appUser;

        public Statistics toEntity() {
            return Statistics.builder()
                    .id(id)
                    .tag(tag)
                    .startDate(startDate)
                    .endDate(endDate)
                    .totalGoals(totalGoals)
                    .achievedGoals(achievedGoals)
                    .achievementPercentage(achievementPercentage)
                    .appUser(appUser)
                    .build();
        }
    }

    @ToString
    @Getter
    public static class Response {
        private final int id;
        private final String tag;
        private final LocalDate startDate;
        private final LocalDate endDate;
        private final int totalGoals;
        private final int achievedGoals;
        private final double achievementPercentage;
        @JsonIgnore
        private final AppUser appUser;


        /* Entity -> Dto */
        public Response(Statistics statistics) {
            this.id = statistics.getId();
            this.tag = statistics.getTag();
            this.startDate = statistics.getStartDate();
            this.endDate = statistics.getEndDate();
            this.totalGoals = statistics.getTotalGoals();
            this.achievedGoals = statistics.getAchievedGoals();
            this.achievementPercentage = statistics.getAchievementPercentage();
            this.appUser = statistics.getAppUser();
        }
    }
}
