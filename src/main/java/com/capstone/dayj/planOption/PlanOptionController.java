package com.capstone.dayj.planOption;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/app-user/{app_user_id}/plan/{plan_id}/option")
public class PlanOptionController {
    private final PlanOptionService planOptionService;
    
    public PlanOptionController(PlanOptionService planOptionService) {
        this.planOptionService = planOptionService;
    }
    
    @GetMapping
    public List<PlanOptionDto.Response> readAllPlanOption(@PathVariable int app_user_id, @PathVariable int plan_id) {
        return planOptionService.readAllPlanDate(app_user_id, plan_id);
    }
    
    @PatchMapping
    public void patchPlan(@PathVariable int app_user_id, @PathVariable int plan_id, @Valid @RequestBody PlanOptionDto.Request dto) {
        planOptionService.updatePlan(app_user_id, plan_id, dto);
    }
}
