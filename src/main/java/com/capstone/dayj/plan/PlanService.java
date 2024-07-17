package com.capstone.dayj.plan;

import com.capstone.dayj.appUser.AppUser;
import com.capstone.dayj.appUser.AppUserRepository;
import com.capstone.dayj.exception.CustomException;
import com.capstone.dayj.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlanService {
    private final PlanRepository planRepository;
    private final AppUserRepository appUserRepository;
    
    @Transactional
    public void createPlan(int app_user_id, PlanDto.Request dto) {
        AppUser appUser = appUserRepository.findById(app_user_id)
                .orElseThrow(() -> new CustomException(ErrorCode.APP_USER_NOT_FOUND));
        dto.setAppUser(appUser);
        planRepository.save(dto.toEntity());
    }
    
    @Transactional
    public List<PlanDto.Response> readAllPlan(int app_user_id) {
        List<Plan> plans = planRepository.findAllByAppUserId(app_user_id);
        
        if (plans.isEmpty())
            throw new CustomException(ErrorCode.PLAN_NOT_FOUND);
        
        return plans.stream().map(PlanDto.Response::new).collect(Collectors.toList());
    }
    
    @Transactional
    public PlanDto.Response readPlanById(int app_user_id, int plan_id) {
        Plan plan = planRepository.findByAppUserIdAndId(app_user_id, plan_id)
                .orElseThrow(() -> new CustomException(ErrorCode.PLAN_NOT_FOUND));
        
        return new PlanDto.Response(plan);
    }
    
    @Transactional
    public PlanDto.Response readPlanByPlanTag(int app_user_id, String planTag) {
        Plan plan = planRepository.findByAppUserIdAndPlanTag(app_user_id, planTag)
                .orElseThrow(() -> new CustomException(ErrorCode.PLAN_NOT_FOUND));
        
        return new PlanDto.Response(plan);
    }
    
    @Transactional
    public void updatePlan(int app_user_id, int plan_id, PlanDto.Request dto) {
        Plan plan = planRepository.findByAppUserIdAndId(app_user_id, plan_id)
                .orElseThrow(() -> new CustomException(ErrorCode.PLAN_NOT_FOUND));
        
        plan.update(dto.getPlanTag(), dto.getGoal(), dto.getPlanPhoto(), dto.isPublic(), dto.isComplete());
    }
    
    @Transactional
    public void deletePlanById(int app_user_id, int plan_id) {
        Plan plan = planRepository.findByAppUserIdAndId(app_user_id, plan_id)
                .orElseThrow(() -> new CustomException(ErrorCode.PLAN_NOT_FOUND));
        PlanDto.Response planDto = new PlanDto.Response(plan);
        planRepository.deleteById(planDto.getId());
    }
}