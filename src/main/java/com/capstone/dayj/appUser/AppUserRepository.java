package com.capstone.dayj.appUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Integer> {
     Optional<AppUser> findByName(String name);
     Optional<AppUser> findByEmail(String email);
     List<AppUser> findByGroupMembers_FriendGroup_Id(int groupId);

}