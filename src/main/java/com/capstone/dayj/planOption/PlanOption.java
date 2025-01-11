package com.capstone.dayj.planOption;

import com.capstone.dayj.common.BaseEntity;
import com.capstone.dayj.plan.Plan;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class PlanOption extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @FutureOrPresent
    @Column(nullable = false)
    private LocalDateTime planStartTime;
    @Future
    @Column(nullable = false)
    private LocalDateTime planEndTime;
    @Future
    private LocalDateTime planAlarmTime;
    @FutureOrPresent
    private LocalDateTime planRepeatStartDate;
    @Future
    private LocalDateTime planRepeatEndDate;
    private List<DayOfWeek> planDaysOfWeek;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", referencedColumnName = "id")
    @JsonBackReference
    private Plan plan;
    
    @PrePersist // Entity가 영속되기 전 작업 수행
    protected void onCreate() {
        if (planStartTime == null)
            this.planStartTime = LocalDateTime.now();
        if (planEndTime == null) {
            this.planEndTime = LocalDateTime.now().plusHours(1);
        }
    }
    
    public void update(PlanOptionDto.Request dto) {
        this.planStartTime = (dto.getPlanStartTime() == null ? this.planStartTime : dto.getPlanStartTime());
        this.planEndTime = (dto.getPlanEndTime() == null ? this.planEndTime : dto.getPlanEndTime());
        this.planAlarmTime = dto.getPlanAlarmTime();
        this.planRepeatStartDate = dto.getPlanRepeatStartDate();
        this.planRepeatEndDate = dto.getPlanRepeatEndDate();
        this.planDaysOfWeek = dto.getPlanDaysOfWeek();
    }
    
    @Builder
    public PlanOption(int id, LocalDateTime planStartTime, LocalDateTime planEndTime, LocalDateTime planAlarmTime, LocalDateTime planRepeatStartDate, LocalDateTime planRepeatEndDate, List<DayOfWeek> planDaysOfWeek, Plan plan) {
        this.id = id;
        this.planStartTime = planStartTime;
        this.planEndTime = planEndTime;
        this.planAlarmTime = planAlarmTime;
        this.planRepeatStartDate = planRepeatStartDate;
        this.planRepeatEndDate = planRepeatEndDate;
        this.planDaysOfWeek = planDaysOfWeek;
        this.plan = plan;
    }
}
