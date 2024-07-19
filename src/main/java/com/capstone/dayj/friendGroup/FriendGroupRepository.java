package com.capstone.dayj.friendGroup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendGroupRepository extends JpaRepository<FriendGroup, Integer> {
    List<FriendGroup> findByGroupMember_AppUser_Id(int appUserId);

    Optional<FriendGroup> findByGroupMember_AppUser_IdAndId(int appUserId, int friendGroupId);
}
