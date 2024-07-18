package com.capstone.dayj.appUser;


import com.capstone.dayj.exception.CustomException;
import com.capstone.dayj.exception.ErrorCode;
import com.capstone.dayj.setting.SettingDto;
import com.capstone.dayj.setting.SettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final SettingRepository settingRepository;
    
    @Transactional
    public void createAppUser(AppUserDto.Request dto) {
        AppUser savedAppUser = appUserRepository.save(dto.toEntity());
        SettingDto.Request newSetting = SettingDto.Request.builder()
                .appUser(savedAppUser)
                .build();
        settingRepository.save(newSetting.toEntity());
    }

    @Transactional(readOnly = true)
    public List<AppUserDto.Response> readAllAppUser() {
        List<AppUser> appUsers = appUserRepository.findAll();
        
        return appUsers.stream().map(AppUserDto.Response::new).collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public AppUserDto.Response readAppUserById(int id) {
        AppUser appUser = appUserRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.APP_USER_NOT_FOUND));
        
        return new AppUserDto.Response(appUser);
    }

    @Transactional(readOnly = true)
    public AppUserDto.Response readAppUserByEmail(String email) {
        AppUser appUser = appUserRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.APP_USER_NOT_FOUND));

        return new AppUserDto.Response(appUser);
    }
    
    @Transactional
    public void updateAppUser(int id, AppUserDto.Request dto) {
        AppUser existingAppUser = appUserRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.APP_USER_NOT_FOUND));
        
        existingAppUser.update(dto.getNickname());
    }
    
    @Transactional
    public void deleteAppUserById(int id) {
        AppUser appUser = appUserRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.APP_USER_NOT_FOUND));
        
        appUserRepository.deleteById(appUser.getId());
    }
}