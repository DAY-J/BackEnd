package com.capstone.dayj.groupMember;

import com.capstone.dayj.appUser.AppUserDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/app-user")
public class GroupMemberController {

    GroupMemberService groupMemberService;

    public GroupMemberController(GroupMemberService groupMemberService) { this.groupMemberService = groupMemberService; }

    @PostMapping("/{email}/group-member/{group_id}")
    public void addAppUserToFriendGroup(@PathVariable int group_id, @PathVariable String email){
        groupMemberService.addMemberToFriendGroup(group_id, email);
    }

    @DeleteMapping("/{app_user_id}/group-member/{group_id}")
    public void exitFriendGroup(@PathVariable int app_user_id , @PathVariable int group_id){
        groupMemberService.deleteGroupMember(app_user_id, group_id);
    }
}
