package com.capstone.dayj.groupMember;

import com.capstone.dayj.appUser.AppUser;
import com.capstone.dayj.appUser.AppUserRepository;
import com.capstone.dayj.exception.CustomException;
import com.capstone.dayj.exception.ErrorCode;
import com.capstone.dayj.friendGroup.FriendGroup;
import com.capstone.dayj.friendGroup.FriendGroupDto;
import com.capstone.dayj.friendGroup.FriendGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GroupMemberService {
    private final GroupMemberRepository groupMemberRepository;
    private final AppUserRepository appUserRepository;
    private final FriendGroupRepository friendGroupRepository;

    public FriendGroupDto.Response addMemberToFriendGroup(int group_id, String email) {
        AppUser findAppUser = appUserRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.APP_USER_NOT_FOUND));
        FriendGroup findFriendGroup = friendGroupRepository.findById(group_id)
                .orElseThrow(() -> new CustomException(ErrorCode.FRIEND_GROUP_NOT_FOUND));

        if (!groupMemberRepository.existsByAppUserAndFriendGroup(findAppUser, findFriendGroup)) {
            GroupMemberDto.Request dto = GroupMemberDto.Request.builder()
                    .appUser(findAppUser)
                    .friendGroup(findFriendGroup)
                    .build();

            groupMemberRepository.save(dto.toEntity());
        } else {
            throw new CustomException(ErrorCode.DUPLICATE_GROUP_MEMBER);
        }

        return new FriendGroupDto.Response(findFriendGroup, findAppUser.getId());
    }

    @Transactional
    public void deleteGroupMember(int app_user_id, int group_id) {
        groupMemberRepository.deleteGroupMemberByAppUserIdAndFriendGroupId(app_user_id, group_id);
    }
}
