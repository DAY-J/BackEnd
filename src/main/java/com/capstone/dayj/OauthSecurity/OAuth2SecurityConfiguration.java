package com.capstone.dayj.OauthSecurity;

import com.capstone.dayj.Oauth.OAuth2AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class OAuth2SecurityConfiguration {
    private final OAuth2AppUserService oAuth2AppUserService;

    @Bean
    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .cors().and()
                .authorizeHttpRequests()
                .requestMatchers("/api/**").authenticated() //api로 시작하는 uri 로그인 필수
                .requestMatchers("/admin/**").hasRole("ADMIN") //admin은 관리자만 접근 가능
                .anyRequest().permitAll() //나머지 uri 모두 접근 허용
                .and().oauth2Login()
                .loginPage("/loginForm") //로그인 하지 않았다면 이동할 uri
                .defaultSuccessUrl("/api/app-user/{user_id}/plan") //로그인 성공 시 이동할 uri
                .userInfoEndpoint() //로그인 완료 후 회원 정보 받기
                .userService(oAuth2AppUserService).and().and().build();
    }
}