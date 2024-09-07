package com.capstone.dayj.jwt;

import com.capstone.dayj.appUser.AppUserDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        
        if (authorization == null || !authorization.startsWith("Bearer ")) { // Authorization 헤더 검증
            System.out.println("token null");
            filterChain.doFilter(request, response); // 해당 필터 종료 후, (req, req)를 다음 필터로 전달
            return;
        }
        
        System.out.println("authorization now");
        String token = authorization.split(" ")[1]; // Bearer 부분 제거 후, 순수 토큰만 획득
        
        if (jwtUtil.isExpired(token)) { // 토큰 소멸 시간 검증
            System.out.println("token expired");
            filterChain.doFilter(request, response);
            return;
        }
        
        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);
        
        AppUserDto.Request userDtoRequest = AppUserDto.Request.builder()
                .username(username)
                .password("dummy") // 매 번 요청마다 DB에서 조회하면 오버헤드가 너무 크기 때문에 임시 비밀번호로 초기화를 진행한다. (ContextHolder에 password는 필수가 아니다)
                .role(role)
                .build();
        
        
        CustomUserDetails customUserDetails = new CustomUserDetails(userDtoRequest.toEntity()); // UserDetails에 회원 정보 객체 담기
        //스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken); // 세션에 사용자 등록
        filterChain.doFilter(request, response); // 다음 필터로 (req, res) 전
    }
}
