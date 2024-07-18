package com.capstone.dayj.appUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Integer> {
     Optional<AppUser> findByName(String name);
     Optional<AppUser> findByEmail(String email);
     @Modifying
     @Query("select m from AppUser m join GroupMember gm on m.id = gm.appUser.id where gm.friendGroup.id = :groupId")
     List<AppUser> findAppUsersByFriendGroupId(@Param("groupId") int group_id);
}