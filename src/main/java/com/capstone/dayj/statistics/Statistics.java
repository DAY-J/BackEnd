package com.capstone.dayj.statistics;

import com.capstone.dayj.appUser.AppUser;
import com.capstone.dayj.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true, exclude = {"appUser"})
public class Statistics extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private LocalDate date; // 일일 통계를 위한 날짜

    @Column(nullable = false)
    private String tag;

    @Column(nullable = false)
    private double achievementPercentage;   // 달성 퍼센트

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id", referencedColumnName = "id")
    private AppUser appUser;

    public void updatePercentage(double achievementPercentage) { this.achievementPercentage = achievementPercentage; }


    @Builder
    public Statistics(int id, LocalDate date, String tag, double achievementPercentage, AppUser appUser) {
        this.id = id;
        this.date = date;
        this.tag = tag;
        this.achievementPercentage = achievementPercentage;
        this.appUser = appUser;
    }

}
