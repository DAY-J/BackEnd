package com.capstone.dayj.friendGroup;

import com.capstone.dayj.appUserFriendGroup.AppUserFriendGroup;
import lombok.*;

import java.util.List;

public class FriendGroupDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {
        private int id;
        private String groupGoal;
        private String groupName;
        private boolean groupExit;

        public FriendGroup toEntity(){
            return FriendGroup.builder()
                    .id(id)
                    .groupGoal(groupGoal)
                    .groupName(groupName)
                    .groupExit(groupExit)
                    .build();
        }
    }

    @Getter
    public static class Response {
        private final int id;
        private final String groupGoal;
        private final String groupName;
        private final boolean groupExit;
        private final List<AppUserFriendGroup> appUserFriendGroup;
        
        public Response(FriendGroup friendGroup){
            this.id = friendGroup.getId();
            this.groupGoal = friendGroup.getGroupGoal();
            this.groupName = friendGroup.getGroupName();
            this.groupExit = friendGroup.isGroupExit();
            this.appUserFriendGroup = friendGroup.getAppUserFriendGroup();
        }

    }
}

