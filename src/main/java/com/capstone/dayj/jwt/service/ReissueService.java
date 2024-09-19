package com.capstone.dayj.jwt.service;

import com.capstone.dayj.exception.CustomException;
import com.capstone.dayj.exception.ErrorCode;
import com.capstone.dayj.jwt.entity.RefreshEntity;
import com.capstone.dayj.jwt.repository.RefreshRepository;
import com.capstone.dayj.jwt.util.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class ReissueService {
    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        //get refresh token
        String refreshToken = request.getHeader("refresh");
        
        if (refreshToken == null) {
            throw new CustomException(ErrorCode.REFRESH_TOKEN_EMPTY);
        }
        
        //expired check
        try {
            jwtUtil.isExpired(refreshToken);
        }
        catch (ExpiredJwtException e) {
            throw new CustomException(ErrorCode.REFRESH_TOKEN_EXPIRED);
        }
        
        // 토큰이 refresh인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(refreshToken);
        
        if (!category.equals("refresh")) {
            throw new CustomException(ErrorCode.TOKEN_INCONSISTENCY);
        }
        
        // DB에 저장되어 있는지 확인
        Boolean isExist = refreshRepository.existsByRefresh(refreshToken);
        if (!isExist) {
            throw new CustomException(ErrorCode.TOKEN_NOT_FOUND);
        }
        
        String username = jwtUtil.getUsername(refreshToken);
        String role = jwtUtil.getRole(refreshToken);
        
        // make new JWT
        String newAccess = jwtUtil.createJwt("access", username, role, 480000L); // 8분
        String newRefresh = jwtUtil.createJwt("refresh", username, role, 86400000L); // 2달
        
        // Refresh 토큰 저장 DB에 기존의 Refresh 토큰 삭제 후 새 Refresh 토큰 저장
        refreshRepository.deleteByRefresh(refreshToken);
        addRefreshEntity(username, newRefresh, 86400000L);
        
        // response
        response.setHeader("access", newAccess);
        response.setHeader("refresh", newRefresh);
        return new ResponseEntity<>(HttpStatus.OK);
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