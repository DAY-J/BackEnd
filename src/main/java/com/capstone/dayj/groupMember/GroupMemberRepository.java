package com.capstone.dayj.groupMember;

import com.capstone.dayj.appUser.AppUser;
import com.capstone.dayj.friendGroup.FriendGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, Integer> {
    void deleteGroupMemberByAppUserIdAndFriendGroupId(int app_user_id, int group_id);

    boolean existsByAppUserAndFriendGroup(AppUser appUser, FriendGroup friendGroup);
}