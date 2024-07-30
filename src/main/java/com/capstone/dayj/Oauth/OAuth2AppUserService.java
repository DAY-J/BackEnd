package com.capstone.dayj.Oauth;

import com.capstone.dayj.appUser.AppUser;
import com.capstone.dayj.appUser.AppUserDto;
import com.capstone.dayj.appUser.AppUserRepository;
import com.capstone.dayj.appUser.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OAuth2AppUserService extends DefaultOAuth2UserService {
    private final BCryptPasswordEncoder encoder;
    private final AppUserRepository appUserRepository;
    private final AppUserService appUserService;

    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getClientId();
        String providerId = oAuth2User.getAttribute("sub");
        String username = provider + "_" + providerId; //중복이 발생하지 않도록 provider와 providerId를 조합
        String email = oAuth2User.getAttribute("email");
        String role = "ROLE_USER"; //일반 유저
        String nickname = randomMix(20); // nickname 설정
        Optional<AppUser> findAppUser = appUserRepository.findByName(username);

        if (findAppUser.isEmpty()) { //찾지 못했다면
            AppUserDto.Request newAppUser = AppUserDto.Request.builder()
                    .name(username)
                    .email(email)
                    .password(encoder.encode("password"))
                    .role(role)
                    .nickname(nickname)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            appUserService.createAppUser(newAppUser);
        }
        return oAuth2User;
    }

    public static String randomMix(int range) {
        StringBuilder sb = new StringBuilder();
        Random rd = new Random();

        for (int i = 0; i < range; i++) {
            if (rd.nextBoolean())
                sb.append(rd.nextInt(10));
            else
                sb.append((char) (rd.nextInt(26) + 65));
        }

        return sb.toString();
    }
}