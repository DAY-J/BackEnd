package com.capstone.dayj;

import com.capstone.dayj.appUser.AppUser;
import com.capstone.dayj.appUser.AppUserDto;
import com.capstone.dayj.appUser.AppUserRepository;
import com.capstone.dayj.appUser.AppUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class AppUserServiceTest {
    @InjectMocks
    private AppUserService appUserService;
    @Mock
    private AppUserRepository appUserRepository;
    @Mock
    private AppUser appUserDto = AppUserDto.Request.builder()
            .username("-")
            .password("-")
            .nickname("085NZB0Y9P64969409KQ")
            .role("ROLE_USER")
            .isAlarm(true)
            .build()
            .toEntity();
    
    @Test
    public void AppUser_POST() {
        int testId = 1;
        lenient().when(appUserRepository.save(appUserDto)).thenReturn(appUserDto);
    }
}
