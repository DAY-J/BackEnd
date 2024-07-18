package com.capstone.dayj.groupMember;

import com.capstone.dayj.appUser.AppUser;
import com.capstone.dayj.friendGroup.FriendGroup;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

public class GroupMemberDto {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {
        private int id;
        private AppUser appUser;
        private FriendGroup friendGroup;
        
        public GroupMember toEntity() {
            return GroupMember.builder()
                    .id(id)
                    .appUser(appUser)
                    .friendGroup(friendGroup)
                    .build();
        }
    }
    
    @Getter
    public static class Response {
        private final int id;
        @JsonIgnore
        private final AppUser appUser;
        @JsonIgnore
        private final FriendGroup friendGroup;
        
        public Response(GroupMember groupMember) {
            this.id = groupMember.getId();
            this.appUser = groupMember.getAppUser();
            this.friendGroup = groupMember.getFriendGroup();
        }
        
    }
}
