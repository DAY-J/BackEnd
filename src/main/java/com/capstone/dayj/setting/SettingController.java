package com.capstone.dayj.setting;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/app-user/{app_user_id}/setting")
public class SettingController {
    private final SettingService settingService;
    
    public SettingController(SettingService settingService) {
        this.settingService = settingService;
    }
    
    @GetMapping
    public SettingDto.Response readSettingByAppUserId(@PathVariable int app_user_id) {
        return settingService.readSettingByAppUserId(app_user_id);
    }
    
    @PatchMapping
    public SettingDto.Response patchSetting(@PathVariable int app_user_id, @Valid @RequestBody SettingDto.Request dto) {
        return settingService.patchSetting(app_user_id, dto);
    }
    
    @PatchMapping("/image")
    public SettingDto.Response patchSettingImage(@PathVariable int app_user_id, MultipartFile image) throws IOException {
        return settingService.patchSettingImage(app_user_id, image);
    }
}