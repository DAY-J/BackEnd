package com.capstone.dayj.groupMember;

import com.capstone.dayj.appUser.AppUser;
import com.capstone.dayj.appUser.AppUserDto;
import com.capstone.dayj.appUser.AppUserRepository;
import com.capstone.dayj.exception.CustomException;
import com.capstone.dayj.exception.ErrorCode;
import com.capstone.dayj.friendGroup.FriendGroup;
import com.capstone.dayj.friendGroup.FriendGroupRepository;
import com.capstone.dayj.plan.PlanDto;
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
        AppUser findAppUser = appUserRepository.findByEmail(email)
                .orElseThrow(()-> new CustomException(ErrorCode.APP_USER_NOT_FOUND));
        FriendGroup findFriendGroup = friendGroupRepository.findById(group_id)
                .orElseThrow(()-> new CustomException(ErrorCode.FRIEND_GROUP_NOT_FOUND));

        // 이미 그룹 회원이라면?
        new GroupMemberDto.Request();
        GroupMemberDto.Request dto = GroupMemberDto.Request.builder()
                .appUser(findAppUser)
                .friendGroup(findFriendGroup)
                .build();

        groupMemberRepository.save(dto.toEntity());
    }

//    @Transactional
//    public List<AppUserDto.Response> readAllMemberInFriendGroup(int app_user_id, int group_id){
//        List<AppUser> findAppUsers = appUserRepository.findByGroupMembers_FriendGroup_Id(group_id).stream()
//                .filter(appUser -> appUser.getId() != app_user_id)
//                .toList();
//
//        if (findAppUsers.isEmpty())
//            throw new CustomException(ErrorCode.APP_USER_NOT_FOUND);
//
//        return findAppUsers.stream().map(AppUserDto.Response::new).collect(Collectors.toList());
//    }

    @Transactional
    public void deleteGroupMember(int app_user_id, int group_id){
        groupMemberRepository.deleteGroupMemberByAppUserIdAndFriendGroupId(app_user_id,group_id);
    }
}
