package com.capstone.dayj.plan;

import com.capstone.dayj.appUser.AppUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;

public class PlanDto {
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {
        private int id;
        private String planPhoto;
        private String planTag;
        private String goal;
        private boolean isComplete;
        private boolean isPublic;
        private LocalDateTime planAlarmTime;
        private LocalDateTime planStartTime;
        private LocalDateTime planEndTime;
        private LocalDateTime planRepeatStartDate;
        private LocalDateTime planRepeatEndDate;
        private AppUser appUser;
        
        public Plan toEntity() {
            return Plan.builder()
                    .id(id)
                    .planPhoto(planPhoto)
                    .planTag(planTag)
                    .goal(goal)
                    .isComplete(isComplete)
                    .isPublic(isPublic)
                    .planAlarmTime(planAlarmTime)
                    .planStartTime(planStartTime)
                    .planEndTime(planEndTime)
                    .planRepeatStartDate(planRepeatStartDate)
                    .planRepeatEndDate(planRepeatEndDate)
                    .appUser(appUser)
                    .build();
        }
    }
    
    @Getter
    public static class Response {
        private final int id;
        private final String planPhoto;
        private final String planTag;
        private final String goal;
        private final boolean isComplete;
        private final boolean isPublic;
        private final LocalDateTime planAlarmTime;
        private final LocalDateTime planStartTime;
        private final LocalDateTime planEndTime;
        private final LocalDateTime planRepeatStartDate;
        private final LocalDateTime planRepeatEndDate;
        @JsonIgnore
        private final AppUser appUser;
        
        /* Entity -> Dto */
        public Response(Plan plan) {
            this.id = plan.getId();
            this.planTag = plan.getPlanTag();
            this.planPhoto = plan.getPlanPhoto();
            this.goal = plan.getGoal();
            this.isComplete = plan.isComplete();
            this.isPublic = plan.isPublic();
            this.planAlarmTime = plan.getPlanAlarmTime();
            this.planStartTime = plan.getPlanStartTime();
            this.planEndTime = plan.getPlanEndTime();
            this.planRepeatStartDate = plan.getPlanRepeatStartDate();
            this.planRepeatEndDate = plan.getPlanRepeatEndDate();
            this.appUser = plan.getAppUser();
        }
    }
}
