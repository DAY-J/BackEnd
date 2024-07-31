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
        private LocalDate date;
        private String tag;
        private double achievementPercentage;
        private AppUser appUser;

        public Statistics toEntity() {
            return Statistics.builder()
                    .id(id)
                    .date(date)
                    .tag(tag)
                    .achievementPercentage(achievementPercentage)
                    .appUser(appUser)
                    .build();
        }
    }

    @ToString
    @Getter
    public static class Response {
        private final int id;
        private final LocalDate date;
        private final String tag;
        private final double achievementPercentage;
        @JsonIgnore
        private final AppUser appUser;


        /* Entity -> Dto */
        public Response(Statistics statistics) {
            this.id = statistics.getId();
            this.date = statistics.getDate();
            this.tag = statistics.getTag();
            this.achievementPercentage = statistics.getAchievementPercentage();
            this.appUser = statistics.getAppUser();
        }
    }
}
