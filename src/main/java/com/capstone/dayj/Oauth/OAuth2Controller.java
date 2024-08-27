package com.capstone.dayj.Oauth;

import com.capstone.dayj.appUser.AppUser;
import com.capstone.dayj.appUser.AppUserDto;
import com.capstone.dayj.appUser.AppUserRepository;
import com.capstone.dayj.appUser.AppUserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class OAuth2Controller {
    private final BCryptPasswordEncoder encoder;
    private final AppUserRepository appUserRepository;
    private final AppUserService appUserService;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @GetMapping("/loginForm")
    public String home() {
        return "loginForm";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "adminPage";
    }

    @PostMapping("/api/auth/google")
    public ResponseEntity<String> handleGoogleLogin(@RequestParam("access_token") String accessToken) {
        System.out.println(accessToken);

        String userInfoEndpointUri = "https://www.googleapis.com/oauth2/v3/userinfo";

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer" + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(userInfoEndpointUri, HttpMethod.GET, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            try {
                JsonNode userInfo = objectMapper.readTree(response.getBody()); // 사용자 정보를 JsonNode로 반환
                String name = userInfo.get("name").asText();
                String email = userInfo.get("email").asText();
                String profilePhoto = userInfo.get("picture").asText();
                String role = "ROLE_USER"; //일반 유저
                String nickname = "test"; // nickname 설정
                Boolean isAlarm = false;

                Optional<AppUser> findAppUser = appUserRepository.findByEmail(email); //이메일로 사용자 존재 여부 확인

                if (findAppUser.isEmpty()) { //찾지 못했다면 db에 저장
                    AppUserDto.Request newAppUser = AppUserDto.Request.builder()
                            .name(name)
                            .email(email)
                            .profilePhoto(profilePhoto)
                            .password(encoder.encode("password"))
                            .role(role)
                            .nickname(nickname)
                            .isAlarm(isAlarm)
                            .build();
                    appUserService.createAppUser(newAppUser);
                    return ResponseEntity.ok("User created successfully");
                }
            } catch (JsonProcessingException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to parse user info"); // json 구문 분석 실패시
            }
        } else {
            return ResponseEntity.status(response.getStatusCode()).body("Failed to fetch user info from Google: "); // 사용자 정보 가져오기 실패시
        }
        return ResponseEntity.ok("User login successfully");
    }
}