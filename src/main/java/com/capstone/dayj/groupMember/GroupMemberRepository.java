package com.capstone.dayj.groupMember;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, Integer> {
    void deleteGroupMemberByAppUserIdAndFriendGroupId(int app_user_id, int group_id);
}