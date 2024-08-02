package com.capstone.dayj.statistics;

import com.capstone.dayj.appUser.AppUser;
import com.capstone.dayj.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Map;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true, exclude = {"appUser"})
public class Statistics extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Convert(converter = MapToJsonConverter.class)
    private Map<LocalDate, Integer> achievementRate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id", referencedColumnName = "id")
    @JsonIgnore
    private AppUser appUser;

    public void update(StatisticsDto.Request dto) {
        this.appUser = dto.getAppUser() == null ? this.appUser : dto.getAppUser();
        this.achievementRate = dto.getAchievementRate() == null ? this.achievementRate : dto.getAchievementRate();
    }

    @Builder
    public Statistics(int id, Map<LocalDate, Integer> achievementRate, AppUser appUser) {
        this.id = id;
        this.achievementRate = achievementRate;
        this.appUser = appUser;
    }

}

