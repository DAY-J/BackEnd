package com.capstone.dayj.setting;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/app-user/{app_user_id}/setting")
public class SettingController {
    private SettingService settingService;
    
    public SettingController(SettingService settingService) {
        this.settingService = settingService;
    }
    
    @GetMapping
    public SettingDto.Response readSettingForUser(@PathVariable int app_user_id) {
        return settingService.readSettingById(app_user_id);
    }
    
    @PatchMapping
    public void patchSetting(@PathVariable int app_user_id, @RequestBody SettingDto.Request dto) {
        settingService.patchSetting(app_user_id, dto);
    }
}