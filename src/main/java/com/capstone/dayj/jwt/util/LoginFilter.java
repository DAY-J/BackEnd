package com.capstone.dayj.jwt.util;

import com.capstone.dayj.config.JWTProperties;
import com.capstone.dayj.jwt.entity.RefreshEntity;
import com.capstone.dayj.jwt.repository.RefreshRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JWTProperties jwtProperties;
    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = obtainUsername(request); // obtain은 UsernamePasswordAuthenticationFilter에 있는 함수
        String password = obtainPassword(request);
        
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null); // DTO, 3번째는 role값
        return authenticationManager.authenticate(authToken);
    }
    
    @Override // 로그인 성공시 실행하는 메소드 (여기서 JWT를 발급)
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
        String username = authentication.getName();
        
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();
        
        // 토큰 생성
        Long expiredAccess = jwtProperties.getExpiredAccess();
        Long expiredRefresh = jwtProperties.getExpiredRefresh();
        String access = jwtUtil.createJwt("access", username, role, expiredAccess);
        String refresh = jwtUtil.createJwt("refresh", username, role, expiredRefresh);
        
        // Refresh 토큰 저장
        addRefreshEntity(username, refresh, expiredRefresh);
        
        // 응답 설정
        response.setHeader("access", access);
        response.setHeader("refresh", refresh);
        response.setStatus(HttpStatus.OK.value());
    }
    
    @Override // 로그인 실패시 실행하는 메소드
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(401);
    }
    
    private void addRefreshEntity(String username, String refresh, Long expiredMs) {
        Date date = new Date(System.currentTimeMillis() + expiredMs);
        
        RefreshEntity refreshEntity = new RefreshEntity();
        refreshEntity.setUsername(username);
        refreshEntity.setRefresh(refresh);
        refreshEntity.setExpiration(date.toString());
        
        refreshRepository.save(refreshEntity);
    }
}