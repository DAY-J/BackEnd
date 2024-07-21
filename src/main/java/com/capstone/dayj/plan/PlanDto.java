package com.capstone.dayj.plan;

import com.capstone.dayj.appUser.AppUser;
import com.capstone.dayj.planOption.PlanOption;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

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
        private PlanOption planOption;
        private AppUser appUser;
        
        public Plan toEntity() {
            return Plan.builder()
                    .id(id)
                    .planPhoto(planPhoto)
                    .planTag(planTag)
                    .goal(goal)
                    .isComplete(isComplete)
                    .isPublic(isPublic)
                    .planOption(planOption)
                    .appUser(appUser)
                    .build();
        }
    }
    
    @ToString
    @Getter
    public static class Response {
        private final int id;
        private final String planPhoto;
        private final String planTag;
        private final String goal;
        private final boolean isComplete;
        private final boolean isPublic;
        private final PlanOption planOption;
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
            this.planOption = plan.getPlanOption();
            this.appUser = plan.getAppUser();
        }
    }
}
