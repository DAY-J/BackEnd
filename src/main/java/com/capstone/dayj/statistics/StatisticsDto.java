package com.capstone.dayj.statistics;

import com.capstone.dayj.appUser.AppUser;
import jakarta.persistence.Column;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.util.Map;

public class StatisticsDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        private Map<LocalDate, Integer> achievementRate;
        private AppUser appUser;

        public Statistics toEntity() {
            return Statistics.builder()
                    .achievementRate(achievementRate)
                    .appUser(appUser)
                    .build();
        }
    }

    @ToString
    @Getter
    public static class Response {
        private final int id;
        private final Map<LocalDate, Integer> achievementRate;

        /* Entity -> Dto */
        public Response(Statistics statistics) {
            this.id = statistics.getId();
            this.achievementRate = statistics.getAchievementRate();
        }
    }
}
