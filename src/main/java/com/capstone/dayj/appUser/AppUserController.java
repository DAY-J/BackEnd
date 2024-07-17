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
    public AppUserDto.Response readAppUserByEmail(@PathVariable String email){
        return appUserService.readAppUserByEmail(email);
    }

    @GetMapping("/{id}")
    public AppUserDto.Response readAppUserById(@PathVariable int id) {
        return appUserService.readAppUserById(id);
    }

    @PatchMapping("/{id}")
    public void patchAppUser(@PathVariable int id, @Valid @RequestBody AppUserDto.Request dto) {
        appUserService.updateAppUser(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteAppUserById(@PathVariable int id) {
        appUserService.deleteAppUserById(id);
    }

}