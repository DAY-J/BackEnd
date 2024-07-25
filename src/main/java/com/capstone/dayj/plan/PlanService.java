package com.capstone.dayj.plan;

import com.capstone.dayj.appUser.AppUser;
import com.capstone.dayj.appUser.AppUserRepository;
import com.capstone.dayj.exception.CustomException;
import com.capstone.dayj.exception.ErrorCode;
import com.capstone.dayj.planOption.PlanOptionDto;
import com.capstone.dayj.planOption.PlanOptionRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlanService {
    private final PlanRepository planRepository;
    private final PlanOptionRepository planOptionRepository;
    private final AppUserRepository appUserRepository;
    
    @Transactional
    public void createPlan(int app_user_id, PlanDto.Request dto) {
        AppUser findAppUser = appUserRepository.findById(app_user_id)
                .orElseThrow(() -> new CustomException(ErrorCode.APP_USER_NOT_FOUND));
        
        dto.setAppUser(findAppUser);
        Plan newPlan = planRepository.save(dto.toEntity());
        
        PlanOptionDto.Request newPlanOption = PlanOptionDto.Request.builder()
                .plan(newPlan)
                .build();
        planOptionRepository.save(newPlanOption.toEntity());
    }
    
    @Transactional(readOnly = true)
    public List<PlanDto.Response> readAllPlan(int app_user_id) {
        List<Plan> findPlans = planRepository.findAllByAppUserId(app_user_id);
        
        if (findPlans.isEmpty())
            throw new CustomException(ErrorCode.PLAN_NOT_FOUND);
        
        return findPlans.stream().map(PlanDto.Response::new).collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public PlanDto.Response readPlanById(int plan_id) {
        Plan findPlan = planRepository.findById(plan_id)
                .orElseThrow(() -> new CustomException(ErrorCode.PLAN_NOT_FOUND));
        
        return new PlanDto.Response(findPlan);
    }
    
    @Transactional(readOnly = true)
    public List<PlanDto.Response> readPlanByPlanTag(String planTag) {
        List<Plan> findPlans = planRepository.findAll()
                .stream().filter(plan -> plan.getPlanTag().equals(planTag)).toList();
        
        if (findPlans.isEmpty())
            throw new CustomException(ErrorCode.PLAN_NOT_FOUND);
        
        return findPlans.stream().map(PlanDto.Response::new).collect(Collectors.toList());
    }
    
    @Transactional
    public void updatePlan(int app_user_id, int plan_id, PlanDto.Request dto) {
        Plan findPlan = planRepository.findByAppUserIdAndId(app_user_id, plan_id)
                .orElseThrow(() -> new CustomException(ErrorCode.PLAN_NOT_FOUND));
        findPlan.update(dto);
    }
    
    @Transactional
    public void deletePlanById(int plan_id) {
        Plan findPlan = planRepository.findById(plan_id)
                .orElseThrow(() -> new CustomException(ErrorCode.PLAN_NOT_FOUND));
        planRepository.delete(findPlan);
    }
}