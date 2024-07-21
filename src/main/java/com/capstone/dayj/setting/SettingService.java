package com.capstone.dayj.setting;


import com.capstone.dayj.exception.CustomException;
import com.capstone.dayj.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SettingService {
    private final SettingRepository settingRepository;
    
    @Transactional(readOnly = true)
    public SettingDto.Response readSettingByAppUserId(int app_user_id) {
        Setting findSetting = settingRepository.findByAppUserId(app_user_id)
                .orElseThrow(() -> new CustomException(ErrorCode.APP_USER_NOT_FOUND));
        return new SettingDto.Response(findSetting);
    }
    
    @Transactional
    public void patchSetting(int app_user_id, SettingDto.Request dto) {
        Setting findSetting = settingRepository.findByAppUserId(app_user_id)
                .orElseThrow(() -> new CustomException(ErrorCode.APP_USER_NOT_FOUND));
        
        findSetting.update(dto.getProfilePhoto(), dto.isAlarm());
    }
}
