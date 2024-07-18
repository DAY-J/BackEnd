package com.capstone.dayj.plan;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/app-user/{app_user_id}/plan")
public class PlanController {
    private final PlanService planService;
    
    public PlanController(PlanService planService) {
        this.planService = planService;
    }
    
    @PostMapping
    public void createPlan(@PathVariable int app_user_id, @Valid @RequestBody PlanDto.Request dto) {
        planService.createPlan(app_user_id, dto);
    }
    
    @GetMapping
    public List<PlanDto.Response> readAllPlan(@PathVariable int app_user_id) {
        return planService.readAllPlan(app_user_id);
    }
    
    @GetMapping("/{post_id}")
    public PlanDto.Response readPlanById(@PathVariable int app_user_id, @PathVariable int post_id) {
        return planService.readPlanById(app_user_id, post_id);
    }
    
    @GetMapping("tag/{plan_tag}")
    public List<PlanDto.Response> readByPlanTag(@PathVariable int app_user_id, @PathVariable String plan_tag) {
        return planService.readPlanByPlanTag(app_user_id, plan_tag);
    }
    
    @PatchMapping("/{plan_id}")
    public void patchPlan(@PathVariable int app_user_id, @PathVariable int plan_id, @Valid @RequestBody PlanDto.Request dto) {
        planService.updatePlan(app_user_id, plan_id, dto);
    }
    
    @DeleteMapping("/{plan_id}")
    public void deletePlanById(@PathVariable int app_user_id, @PathVariable int plan_id) {
        planService.deletePlanById(app_user_id, plan_id);
    }
}
