package com.capstone.dayj.setting;

import com.capstone.dayj.appUser.AppUser;
import lombok.*;

public class SettingDto {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {
        private int id;
        private String profilePhoto;
        private boolean alarm;
        private AppUser appUser;
        
        public Setting toEntity() {
            return Setting.builder()
                    .id(id)
                    .profilePhoto(profilePhoto)
                    .alarm(alarm)
                    .appUser(appUser)
                    .build();
        }
    }
    
    @Getter
    public static class Response {
        private final int id;
        private final String profilePhoto;
        private final boolean alarm;
        
        public Response(Setting setting) {
            this.id = setting.getId();
            this.profilePhoto = setting.getProfilePhoto();
            this.alarm = setting.isAlarm();
        }
        
    }
}
