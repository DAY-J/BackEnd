package com.capstone.dayj.statistics;

import com.capstone.dayj.appUser.AppUser;
import lombok.*;

import java.time.LocalDate;

public class StatisticsDto {

    @Data
    @Builder
    public static class Request {
        private LocalDate date;
        private Long achievementRate;
        private AppUser appUser;

        public Statistics toEntity() {
            return Statistics.builder()
                    .date(date)
                    .achievementRate(achievementRate)
                    .appUser(appUser)
                    .build();
        }
    }

    @ToString
    @Getter
    public static class Response {
        private final int id;
        private final LocalDate date;
        private final Long achievementRate;

        /* Entity -> Dto */
        public Response(Statistics statistics) {
            this.id = statistics.getId();
            this.date = statistics.getDate();
            this.achievementRate = statistics.getAchievementRate();
        }
    }
}
