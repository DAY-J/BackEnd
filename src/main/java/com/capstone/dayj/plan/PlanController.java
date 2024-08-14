package com.capstone.dayj.plan;

import com.capstone.dayj.planOption.PlanOptionDto;
import com.capstone.dayj.tag.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/app-user/{app_user_id}/plan")
public class PlanController {
    private final PlanService planService;
    
    public PlanController(PlanService planService) {
        this.planService = planService;
    }
    
    @PostMapping
    public PlanDto.Response createPlan(@PathVariable int app_user_id,
                                       @Valid @RequestPart(value = "plan") PlanDto.Request planDto,
                                       @Valid @RequestPart(value = "planOption") PlanOptionDto.Request planOptionDto) {
        return planService.createPlan(app_user_id, planDto, planOptionDto);
    }
    
    @GetMapping
    public List<PlanDto.Response> readAllPlanByDate(@PathVariable int app_user_id, @RequestParam(name = "date", required = false) LocalDate date) {
        return planService.readAllPlanByDate(app_user_id, date);
    }
    
    @GetMapping("tag/{plan_tag}")
    public List<PlanDto.Response> readAllPlanByPlanTag(@PathVariable int app_user_id, @PathVariable Tag plan_tag
            , @RequestParam(name = "date") LocalDate date) {
        return planService.readAllPlanByPlanTag(app_user_id, plan_tag, date);
    }
    
    @GetMapping("/{plan_id}")
    public PlanDto.Response readPlanById(@PathVariable int plan_id) {
        return planService.readPlanById(plan_id);
    }
    
    @GetMapping("/recommend/{tag}")
    public Set<String> recommendPlan(@PathVariable int app_user_id, @PathVariable Tag tag) {
        return planService.recommendPlan(app_user_id, tag);
    }
    
    @PatchMapping("/{plan_id}")
    public PlanDto.Response patchPlan(@PathVariable int plan_id,
                                      @Valid @RequestPart(value = "plan") PlanDto.Request planDto,
                                      @Valid @RequestPart(value = "planOption") PlanOptionDto.Request planOptionDto) {
        return planService.patchPlan(plan_id, planDto, planOptionDto);
    }
    
    @PatchMapping("/{plan_id}/image")
    public PlanDto.Response patchPlanImage(@PathVariable int plan_id, MultipartFile image) throws IOException {
        return planService.patchPlanImage(plan_id, image);
    }
    
    @DeleteMapping("/{plan_id}")
    public String deletePlanById(@PathVariable int plan_id) {
        return planService.deletePlanById(plan_id);
    }
}
