package com.capstone.dayj.friendGroup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendGroupRepository extends JpaRepository<FriendGroup, Integer> {

    @Modifying
    @Query("select fg from FriendGroup fg join GroupMember gm on fg.id = gm.friendGroup.id where gm.appUser.id = :appUserId")
    List<FriendGroup> findFriendGroupByAppUserId(@Param("appUserId") int app_user_id);

    @Query("select fg from FriendGroup fg join GroupMember gm on fg.id = gm.friendGroup.id where gm.appUser.id = :appUserId and gm.friendGroup.id = :friendGroupId")
    Optional<FriendGroup> findFriendGroupByAppUserIdAndGroupId(@Param("appUserId") int app_user_id, @Param("friendGroupId") int friend_group_id);
}
