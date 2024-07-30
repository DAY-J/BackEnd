package com.capstone.dayj.plan;

import com.capstone.dayj.tag.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/app-user/{app_user_id}/plan")
public class PlanController {
    private final PlanService planService;
    
    public PlanController(PlanService planService) {
        this.planService = planService;
    }
    
    @PostMapping
    public PlanDto.Response createPlan(@PathVariable int app_user_id, @Valid @RequestBody PlanDto.Request dto) throws IOException {
        return planService.createPlan(app_user_id, dto);
    }
    
    @GetMapping
    public List<PlanDto.Response> readAllPlan(@PathVariable int app_user_id) {
        return planService.readAllPlan(app_user_id);
    }
    
    @GetMapping("tag/{plan_tag}")
    public List<PlanDto.Response> readAllPlanByPlanTag(@PathVariable int app_user_id, @PathVariable Tag plan_tag) {
        return planService.readAllPlanByPlanTag(app_user_id, plan_tag);
    }
    
    @GetMapping("/{plan_id}")
    public PlanDto.Response readPlanById(@PathVariable int plan_id) {
        return planService.readPlanById(plan_id);
    }
    
    @PatchMapping("/{plan_id}")
    public PlanDto.Response patchPlan(@PathVariable int plan_id, @Valid @RequestBody PlanDto.Request dto) {
        return planService.patchPlan(plan_id, dto);
    }
    
    @PatchMapping("/{plan_id}/image")
    public PlanDto.Response patchPlanImage(@PathVariable int plan_id, MultipartFile image) throws IOException {
        return planService.patchPlanImage(plan_id, image);
    }
    
    @DeleteMapping("/{plan_id}")
    public void deletePlanById(@PathVariable int plan_id) {
        planService.deletePlanById(plan_id);
    }
}
