package com.capstone.dayj.friendGroup;

import com.capstone.dayj.appUser.AppUser;
import com.capstone.dayj.appUser.AppUserRepository;
import com.capstone.dayj.groupMember.GroupMemberDto;
import com.capstone.dayj.groupMember.GroupMemberRepository;
import com.capstone.dayj.exception.CustomException;
import com.capstone.dayj.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendGroupService {
    private final FriendGroupRepository friendGroupRepository;
    private final AppUserRepository appUserRepository;
    private final GroupMemberRepository groupMemberRepository;

    @Transactional
    public void createFriendGroup(int app_user_id, FriendGroupDto.Request dto) {
        FriendGroup friendGroup = dto.toEntity();

        AppUser findAppUser = appUserRepository.findById(app_user_id)
                .orElseThrow(()-> new CustomException(ErrorCode.APP_USER_NOT_FOUND));

        new GroupMemberDto.Request();
        GroupMemberDto.Request request = GroupMemberDto.Request.builder()
                .appUser(findAppUser)
                .friendGroup(friendGroup)
                .build();

        friendGroupRepository.save(friendGroup);
        groupMemberRepository.save(request.toEntity());
    }

    @Transactional(readOnly = true)
    public List<FriendGroupDto.Response> readAllFriendGroup(int app_user_id) {

        List<FriendGroup> findFriendGroups = friendGroupRepository.findByGroupMember_AppUser_Id(app_user_id);

        if (findFriendGroups.isEmpty())
            throw new CustomException(ErrorCode.FRIEND_GROUP_NOT_FOUND);
        
        return findFriendGroups.stream().map(FriendGroupDto.Response::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public FriendGroupDto.Response readFriendGroupById(int app_user_id, int group_id) {

        FriendGroup findFriendGroup = friendGroupRepository.findByGroupMember_AppUser_IdAndId(app_user_id, group_id)
                .orElseThrow(()-> new CustomException(ErrorCode.FRIEND_GROUP_NOT_FOUND));

        return new FriendGroupDto.Response(findFriendGroup);
    }

    @Transactional
    public void updateGroupName(int app_user_id, int group_id, FriendGroupDto.Request dto) {
        FriendGroup findFriendGroup = friendGroupRepository.findByGroupMember_AppUser_IdAndId(app_user_id, group_id)
                .orElseThrow(() -> new CustomException(ErrorCode.FRIEND_GROUP_NOT_FOUND));

        findFriendGroup.updateGroupName(dto);
    }

    @Transactional
    public void updateGroupGoal(int app_user_id, int group_id, FriendGroupDto.Request dto){
        FriendGroup findFriendGroup = friendGroupRepository.findByGroupMember_AppUser_IdAndId(app_user_id,group_id)
                .orElseThrow(()-> new CustomException(ErrorCode.FRIEND_GROUP_NOT_FOUND));

        findFriendGroup.updateGroupGoal(dto);
        friendGroupRepository.save(findFriendGroup);
    }

    @Transactional
    public void deleteFriendGroupById(int group_id) {
        friendGroupRepository.findById(group_id)
                .orElseThrow(() -> new CustomException(ErrorCode.FRIEND_GROUP_NOT_FOUND));
        
        friendGroupRepository.deleteById(group_id);
    }
}
