package com.capstone.dayj.appUser;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/app-user")
public class AppUserController {
    AppUserService appUserService;
    
    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }
    
    @PostMapping
    public void createAppUser(@Valid @RequestBody AppUserDto.Request appUser) {
        appUserService.createAppUser(appUser);
    }
    
    @GetMapping
    public List<AppUserDto.Response> readAllAppUser() {
        return appUserService.readAllAppUser();
    }
    
    @GetMapping("email/{email}")
    public AppUserDto.Response readAppUserByEmail(@PathVariable String email) {
        return appUserService.readAppUserByEmail(email);
    }
    
    @GetMapping("/{app_user_id}")
    public AppUserDto.Response readAppUserById(@PathVariable int app_user_id) {
        return appUserService.readAppUserById(app_user_id);
    }
    
    @PatchMapping("/{app_user_id}")
    public void patchNickname(@PathVariable int app_user_id, @Valid @RequestBody AppUserDto.Request dto) {
        appUserService.updateAppUser(app_user_id, dto);
    }
    
    @DeleteMapping("/{app_user_id}")
    public void deleteAppUserById(@PathVariable int app_user_id) {
        appUserService.deleteAppUserById(app_user_id);
    }
    
}