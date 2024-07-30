package com.capstone.dayj.setting;

import com.capstone.dayj.exception.CustomException;
import com.capstone.dayj.exception.ErrorCode;
import com.capstone.dayj.util.ImageUploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class SettingService {
    private final SettingRepository settingRepository;
    private final ImageUploader imageUploader;
    
    @Transactional(readOnly = true)
    public SettingDto.Response readSettingByAppUserId(int app_user_id) {
        Setting findSetting = settingRepository.findByAppUserId(app_user_id)
                .orElseThrow(() -> new CustomException(ErrorCode.APP_USER_NOT_FOUND));
        return new SettingDto.Response(findSetting);
    }
    
    @Transactional
    public SettingDto.Response patchSetting(int app_user_id, SettingDto.Request dto) {
        Setting findSetting = settingRepository.findByAppUserId(app_user_id)
                .orElseThrow(() -> new CustomException(ErrorCode.APP_USER_NOT_FOUND));
        findSetting.update(dto);
        return new SettingDto.Response(findSetting);
    }
    
    @Transactional
    public SettingDto.Response patchSettingImage(int app_user_id, MultipartFile image) throws IOException {
        Setting findSetting = settingRepository.findByAppUserId(app_user_id)
                .orElseThrow(() -> new CustomException(ErrorCode.APP_USER_NOT_FOUND));
        
        SettingDto.Request dto = new SettingDto.Request();
        dto.setProfilePhoto(imageUploader.upload(image));
        findSetting.update(dto);
        return new SettingDto.Response(findSetting);
    }
}
