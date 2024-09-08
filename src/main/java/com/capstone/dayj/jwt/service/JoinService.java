package com.capstone.dayj.jwt.service;


import com.capstone.dayj.appUser.AppUserDto;
import com.capstone.dayj.appUser.AppUserRepository;
import com.capstone.dayj.exception.CustomException;
import com.capstone.dayj.exception.ErrorCode;
import com.capstone.dayj.jwt.dto.JoinDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinService {
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    
    public void joinProcess(JoinDTO dto) {
        String username = dto.getUsername();
        String password = dto.getPassword();
        String nickname = dto.getNickname();
        
        if (appUserRepository.existsByUsername(username)) {
            throw new CustomException(ErrorCode.DUPLICATE_NICKNAME);
        }
        
        AppUserDto.Request appUserDtoRequest = AppUserDto.Request.builder()
                .username(username)
                .password(bCryptPasswordEncoder.encode(password))
                .role("ROLE_USER")
                .nickname(nickname)
                .isAlarm(false)
                .build();
        appUserRepository.save(appUserDtoRequest.toEntity());
    }
}