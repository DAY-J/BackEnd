package com.capstone.dayj.plan;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/app-user/{user_id}/plan")
public class PlanController {
    private final PlanService planService;
    
    public PlanController(PlanService planService) {
        this.planService = planService;
    }
    
    @PostMapping
    public void createPlan(@PathVariable int user_id, @Valid @RequestBody PlanDto.Request dto) {
        planService.createPlan(user_id, dto);
    }
    
    @GetMapping
    public List<PlanDto.Response> readAllPlan(@PathVariable int user_id) {
        return planService.readAllPlan(user_id);
    }
    
    @GetMapping("/{post_id}")
    public PlanDto.Response readPlanById(@PathVariable int post_id) {
        return planService.readPlanById(post_id);
    }
    
    @GetMapping("tag/{plan_tag}")
    public PlanDto.Response readByPlanTag(@PathVariable String plan_tag) {
        return planService.readPlanByPlanTag(plan_tag);
    }
    
    @PatchMapping("/{plan_id}")
    public void patchPlan(@PathVariable int plan_id, @Valid @RequestBody PlanDto.Request dto) {
        planService.updatePlan(plan_id, dto);
    }
    
    @DeleteMapping("/{plan_id}")
    public void deletePlanById(@PathVariable int plan_id) {
        planService.deletePlanById(plan_id);
    }
}
