package com.capstone.dayj.groupMember;

import com.capstone.dayj.appUser.AppUser;
import com.capstone.dayj.appUser.AppUserDto;
import com.capstone.dayj.appUser.AppUserRepository;
import com.capstone.dayj.exception.CustomException;
import com.capstone.dayj.exception.ErrorCode;
import com.capstone.dayj.friendGroup.FriendGroup;
import com.capstone.dayj.friendGroup.FriendGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupMemberService {
    private final GroupMemberRepository groupMemberRepository;
    private final AppUserRepository appUserRepository;
    private final FriendGroupRepository friendGroupRepository;

    public void addMemberToFriendGroup(int group_id, String email){
        AppUser member = appUserRepository.findByEmail(email)
                .orElseThrow(()-> new CustomException(ErrorCode.APP_USER_NOT_FOUND));
        FriendGroup friendGroup = friendGroupRepository.findById(group_id)
                .orElseThrow(()-> new CustomException(ErrorCode.FRIEND_GROUP_NOT_FOUND));

        // 이미 그룹 회원이라면?

        GroupMemberDto.Request dto = new GroupMemberDto.Request();
        dto.setAppUser(member);
        dto.setFriendGroup(friendGroup);

        groupMemberRepository.save(dto.toEntity());
    }

    @Transactional
    public List<AppUserDto.Response> readAllMemberInFriendGroup(int app_user_id, int group_id){
        List<AppUser> appUsers = appUserRepository.findAppUsersByFriendGroupId(group_id);

        AppUser me = appUserRepository.findById(app_user_id).orElseThrow(()->new CustomException(ErrorCode.APP_USER_NOT_FOUND));
        appUsers.remove(me);
        // 본인 제외한 AppUser 리스트 보여주기

        if (appUsers.isEmpty())
            throw new CustomException(ErrorCode.APP_USER_NOT_FOUND);

        return appUsers.stream().map(AppUserDto.Response::new).collect(Collectors.toList());
    }

    @Transactional
    public void deleteGroupMember(int app_user_id, int group_id){
        groupMemberRepository.deleteGroupMemberByAppUserIdAndFriendGroupId(app_user_id,group_id);
    }
}
