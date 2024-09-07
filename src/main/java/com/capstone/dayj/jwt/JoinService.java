package com.capstone.dayj.jwt;


import com.capstone.dayj.appUser.AppUserDto;
import com.capstone.dayj.appUser.AppUserRepository;
import com.capstone.dayj.util.RandomNickname;
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
        
        if (appUserRepository.existsByUsername(username)) {
            System.out.println("User already exists");
            return;
        }
        
        AppUserDto.Request appUserDtoRequest = AppUserDto.Request.builder()
                .username(username)
                .password(bCryptPasswordEncoder.encode(password))
                .role("ROLE_USER")
                .nickname(RandomNickname.randomMix(10))
                .isAlarm(false)
                .build();
        appUserRepository.save(appUserDtoRequest.toEntity());
    }
}