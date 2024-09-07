package com.capstone.dayj.appUser;

import lombok.*;

public class AppUserDto {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {
        private int id; //기본키
        private String username; //유저 구글 이메일
        private String password; //유저 비밀번호
        private String role; //유저 권한
        private String nickname; // 유저 닉네임, 중복 불가, 친구 그룹 추가에 사용
        private String profilePhoto;
        private Boolean isAlarm;
        
        public AppUser toEntity() {
            return AppUser.builder()
                    .id(id)
                    .username(username)
                    .password(password)
                    .role(role)
                    .nickname(nickname)
                    .profilePhoto(profilePhoto)
                    .isAlarm(isAlarm)
                    .build();
        }
    }
    
    @Getter
    public static class Response {
        private final int id; //기본키
        private final String username; //유저 구글 이메일
        private final String password; //유저 비밀번호
        private final String role; //유저 권한
        private final String nickname; // 유저 닉네임, 중복 불가, 친구 그룹 추가에 사용
        private final String profilePhoto;
        private final Boolean isAlarm;
        
        public Response(AppUser appUser) {
            this.id = appUser.getId();
            this.username = appUser.getUsername();
            this.password = appUser.getPassword();
            this.role = appUser.getRole();
            this.nickname = appUser.getNickname();
            this.profilePhoto = appUser.getProfilePhoto();
            this.isAlarm = appUser.getIsAlarm();
        }
        
    }
}
