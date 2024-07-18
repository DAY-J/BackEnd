package com.capstone.dayj.friendGroup;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/app-user/{app_user_id}/friend-group")
public class FriendGroupController {
    FriendGroupService friendGroupService;

    public FriendGroupController(FriendGroupService friendGroupService) {
        this.friendGroupService = friendGroupService;
    }
    
    @PostMapping
    public void createFriendGroup(@PathVariable int app_user_id, @Valid @RequestBody FriendGroupDto.Request dto) {
        friendGroupService.createFriendGroup(app_user_id, dto);
    }
    
    @GetMapping// 유저 본인이 소속된 그룹 모두 보여주기
    public List<FriendGroupDto.Response> readAllFriendGroup(@PathVariable int app_user_id) {
        return friendGroupService.readAllFriendGroup(app_user_id);
    }

    @GetMapping("/{group_id}") // 유저 본인이 소속된 그룹 중 그룹 아이디에 해당하는 그룹 보여주기
    public FriendGroupDto.Response readFriendGroupById(@PathVariable int app_user_id, @PathVariable int group_id) { return friendGroupService.readFriendGroupById(app_user_id, group_id); }
    
    @PatchMapping("/{group_id}/group-name")
    public void patchGroupName(@PathVariable int app_user_id, @PathVariable int group_id, @Valid @RequestBody FriendGroupDto.Request dto) {
        friendGroupService.updateGroupName(app_user_id, group_id, dto);
    }

    @PatchMapping("/{group_id}/group-goal")
    public void patchGroupGoal(@PathVariable int app_user_id, @PathVariable int group_id, @Valid @RequestBody FriendGroupDto.Request dto){
        friendGroupService.updateGroupGoal(app_user_id, group_id, dto);
    }
    
    @DeleteMapping("/{group_id}") // 그룹 자체 삭제
    public void deleteFriendGroupById(@PathVariable int group_id) {
        friendGroupService.deleteFriendGroupById(group_id);
    }
}
