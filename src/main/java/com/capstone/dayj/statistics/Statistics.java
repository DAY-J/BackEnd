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
    private LocalDate startDate;        // 통계 기간의 시작날짜

    @Column(nullable = false)
    private LocalDate endDate;          // 통계 기간의 종료날짜

    @Column(nullable = false)
    private String tag;

    @Column(nullable = false)
    private int totalGoals;             // 총 목표수

    @Column(nullable = false)
    private int achievedGoals;          // 완료한 목표수

    @Column(nullable = false)
    private double achievementPercentage;   // 퍼센트로 환산

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id", referencedColumnName = "id")
    private AppUser appUser;

    public void updatePercentage(double achievementPercentage) { this.achievementPercentage = achievementPercentage; }

    public Statistics calculateStatistics(int totalGoals, int achievedGoals) {
        double achievementPercentage;

        if (totalGoals == 0) {
            achievementPercentage = 0;
        }
        else {
            achievementPercentage = ((double) achievedGoals / totalGoals) * 100;
        }
        return new Statistics(id, startDate, endDate, tag, totalGoals, achievedGoals, achievementPercentage, appUser);
    }

    @Builder
    public Statistics(int id, LocalDate startDate, LocalDate endDate, String tag, int totalGoals, int achievedGoals, double achievementPercentage, AppUser appUser) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tag = tag;
        this.totalGoals = totalGoals;
        this.achievedGoals = achievedGoals;
        this.achievementPercentage = achievementPercentage;
        this.appUser = appUser;
    }

}
