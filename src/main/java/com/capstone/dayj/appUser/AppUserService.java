package com.capstone.dayj.appUser;


import com.capstone.dayj.exception.CustomException;
import com.capstone.dayj.exception.ErrorCode;
import com.capstone.dayj.util.ImageUploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final ImageUploader imageUploader;
    
    @Transactional
    public AppUserDto.Response createAppUser(AppUserDto.Request dto) {
        if (appUserRepository.existsByNickname(dto.getNickname())) {
            throw new CustomException(ErrorCode.DUPLICATE_NICKNAME);
        }
        
        AppUser savedAppUser = appUserRepository.save(dto.toEntity());
        return new AppUserDto.Response(savedAppUser);
    }
    
    @Transactional(readOnly = true)
    public List<AppUserDto.Response> readAllAppUser() {
        List<AppUser> findAppUsers = appUserRepository.findAll();
        
        return findAppUsers.stream().map(AppUserDto.Response::new).collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public AppUserDto.Response readAppUserById(int id) {
        AppUser findAppUser = appUserRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.APP_USER_NOT_FOUND));
        
        return new AppUserDto.Response(findAppUser);
    }
    
    @Transactional(readOnly = true)
    public AppUserDto.Response readAppUserByUsername(String username) {
        AppUser findAppUser = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.APP_USER_NOT_FOUND));
        
        return new AppUserDto.Response(findAppUser);
    }
    
    @Transactional
    public AppUserDto.Response patchAppUser(int id, AppUserDto.Request dto) {
        if (appUserRepository.existsByNickname(dto.getNickname())) {
            throw new CustomException(ErrorCode.DUPLICATE_NICKNAME);
        }
        
        AppUser findAppUser = appUserRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.APP_USER_NOT_FOUND));
        findAppUser.update(dto);
        return new AppUserDto.Response(findAppUser);
    }
    
    // TODO
//    @Transactional
//    public AppUserDto.Response patchProfileImage(int app_user_id, MultipartFile image) throws IOException {
//        if (image == null || image.isEmpty()) {
//            throw new CustomException(ErrorCode.IMAGE_UPLOAD_FAIL);
//        }
//
//        AppUser findAppUser = appUserRepository.findById(app_user_id)
//                .orElseThrow(() -> new CustomException(ErrorCode.APP_USER_NOT_FOUND));
//
//        AppUserDto.Request dto = AppUserDto.Request.builder()
//                .profilePhoto(imageUploader.upload(image))
//                .build();
//        findAppUser.update(dto);
//        return new AppUserDto.Response(findAppUser);
//    }
    
    
    @Transactional
    public AppUserDto.Response deleteAppUserById(int id) {
        AppUser findAppUser = appUserRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.APP_USER_NOT_FOUND));
        AppUserDto.Response dto = new AppUserDto.Response(findAppUser);
        
        appUserRepository.deleteById(findAppUser.getId());
        return dto;
    }
}