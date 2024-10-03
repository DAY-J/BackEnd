package com.capstone.dayj.planOption;

import com.capstone.dayj.plan.Plan;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

public class PlanOptionDto {
    @Data
    @Builder
    public static class Request {
        private LocalDateTime planAlarmTime;
        private LocalDateTime planStartTime;
        private LocalDateTime planEndTime;
        private LocalDateTime planRepeatStartDate;
        private LocalDateTime planRepeatEndDate;
        private List<DayOfWeek> planDaysOfWeek;
        private Plan plan;
        
        public PlanOption toEntity() {
            return PlanOption.builder()
                    .planAlarmTime(planAlarmTime)
                    .planStartTime(planStartTime)
                    .planEndTime(planEndTime)
                    .planRepeatStartDate(planRepeatStartDate)
                    .planRepeatEndDate(planRepeatEndDate)
                    .planDaysOfWeek(planDaysOfWeek)
                    .plan(plan)
                    .build();
        }
    }
    
    @Getter
    public static class Response {
        private final int id;
        private final LocalDateTime planAlarmTime;
        private final LocalDateTime planStartTime;
        private final LocalDateTime planEndTime;
        private final LocalDateTime planRepeatStartDate;
        private final LocalDateTime planRepeatEndDate;
        private final List<DayOfWeek> planDaysOfWeek;
        
        /* Entity -> Dto */
        public Response(PlanOption planOption) {
            this.id = planOption.getId();
            this.planAlarmTime = planOption.getPlanAlarmTime();
            this.planStartTime = planOption.getPlanStartTime();
            this.planEndTime = planOption.getPlanEndTime();
            this.planRepeatStartDate = planOption.getPlanRepeatStartDate();
            this.planRepeatEndDate = planOption.getPlanRepeatEndDate();
            this.planDaysOfWeek = planOption.getPlanDaysOfWeek();
        }
    }
}
