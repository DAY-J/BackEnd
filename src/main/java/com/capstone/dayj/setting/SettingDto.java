package com.capstone.dayj.setting;

import com.capstone.dayj.appUser.AppUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.List;

public class SettingDto {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {
        private int id;
        private List<AlarmSetting> alarmSettings;
        private String profilePhoto;
        private AppUser appUser;
        
        public Setting toEntity() {
            return Setting.builder()
                    .id(id)
                    .alarmSettings(alarmSettings)
                    .profilePhoto(profilePhoto)
                    .appUser(appUser)
                    .build();
        }
    }
    
    @Getter
    public static class Response {
        private final int id;
        private final List<AlarmSetting> alarmSettings;
        private final String profilePhoto;
        @JsonIgnore
        private final AppUser appUser;
        
        public Response(Setting setting) {
            this.id = setting.getId();
            this.alarmSettings = setting.getAlarmSettings();
            this.profilePhoto = setting.getProfilePhoto();
            this.appUser = setting.getAppUser();
        }
        
    }
}
