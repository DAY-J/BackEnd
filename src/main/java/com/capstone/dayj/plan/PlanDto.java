package com.capstone.dayj.plan;

import com.capstone.dayj.appUser.AppUser;
import com.capstone.dayj.planOption.PlanOption;
import com.capstone.dayj.planOption.PlanOptionDto;
import com.capstone.dayj.tag.Tag;
import lombok.*;

import java.time.LocalDateTime;

public class PlanDto {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {
        private int id;
        private Tag planTag;
        private String planPhoto;
        private String goal;
        private Boolean isComplete;
        private Boolean isPublic;
        private PlanOption planOption;
        private AppUser appUser;
        
        public Plan toEntity() {
            return Plan.builder()
                    .id(id)
                    .planTag(planTag)
                    .planPhoto(planPhoto)
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
        private final Tag planTag;
        private final String planPhoto;
        private final String goal;
        private final Boolean isComplete;
        private final Boolean isPublic;
        private final PlanOptionDto.Response planOption;
        
        /* Entity -> Dto */
        public Response(Plan plan) {
            this.id = plan.getId();
            this.planTag = plan.getPlanTag();
            this.planPhoto = plan.getPlanPhoto();
            this.goal = plan.getGoal();
            this.isComplete = plan.getIsComplete();
            this.isPublic = plan.getIsPublic();
            this.planOption = new PlanOptionDto.Response(plan.getPlanOption());
        }
    }
    
    @Getter
    public static class groupResponse {
        private final int id;
        private final String goal;
        private final Boolean isComplete;
        private final LocalDateTime createdAt;
        private final LocalDateTime updatedAt;
        
        public groupResponse(Plan plan) {
            this.id = plan.getId();
            this.goal = plan.getGoal();
            this.isComplete = plan.getIsComplete();
            this.createdAt = plan.getCreatedAt();
            this.updatedAt = plan.getUpdatedAt();
        }
    }
}
