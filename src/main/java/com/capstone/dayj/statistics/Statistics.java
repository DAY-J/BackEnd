package com.capstone.dayj.statistics;

import com.capstone.dayj.appUser.AppUser;
import com.capstone.dayj.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true, exclude = {"appUser"})
public class Statistics extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate date;

    @ColumnDefault("0")
    private Long achievementRate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id", referencedColumnName = "id")
    @JsonIgnore
    private AppUser appUser;

    @Transactional
    public void update(StatisticsDto.Request dto) {
        this.appUser = (dto.getAppUser() == null ? this.appUser : dto.getAppUser());
        this.date = (dto.getDate() == null ? this.date : dto.getDate());
        this.achievementRate = (dto.getAchievementRate() == null ? this.achievementRate : dto.getAchievementRate());
    }

    @Builder
    public Statistics(int id, LocalDate date, Long achievementRate, AppUser appUser) {
        this.id = id;
        this.date = date;
        this.achievementRate = achievementRate;
        this.appUser = appUser;
    }

}

