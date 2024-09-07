package com.capstone.dayj.appUser;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/app-user")
public class AppUserController {
    AppUserService appUserService;
    
    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }
    
    @PostMapping
    public AppUserDto.Response createAppUser(@Valid @RequestBody AppUserDto.Request appUser) {
        return appUserService.createAppUser(appUser);
    }
    
    @GetMapping
    public List<AppUserDto.Response> readAllAppUser() {
        return appUserService.readAllAppUser();
    }
    
    @GetMapping("/{app_user_id}")
    public AppUserDto.Response readAppUserById(@PathVariable int app_user_id) {
        return appUserService.readAppUserById(app_user_id);
    }
    
    @GetMapping("username/{username}")
    public AppUserDto.Response readAppUserByUsername(@PathVariable String username) {
        return appUserService.readAppUserByUsername(username);
    }
    
    
    @PatchMapping("/{app_user_id}")
    public AppUserDto.Response patchAppUser(@PathVariable int app_user_id, @Valid @RequestBody AppUserDto.Request dto) {
        return appUserService.patchAppUser(app_user_id, dto);
    }
    
    @PatchMapping("/image")
    public AppUserDto.Response patchSettingImage(@PathVariable int app_user_id, MultipartFile image) throws IOException {
        return appUserService.patchProfileImage(app_user_id, image);
    }
    
    @DeleteMapping("/{app_user_id}")
    public AppUserDto.Response deleteAppUserById(@PathVariable int app_user_id) {
        return appUserService.deleteAppUserById(app_user_id);
    }
    
}