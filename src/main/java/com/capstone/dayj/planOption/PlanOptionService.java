package com.capstone.dayj.planOption;

import com.capstone.dayj.exception.CustomException;
import com.capstone.dayj.exception.ErrorCode;
import com.capstone.dayj.plan.Plan;
import com.capstone.dayj.plan.PlanRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanOptionService {
    private final PlanRepository planRepository;
    
    @Transactional
    public PlanOptionDto.Response readPlanDate(int app_user_id, int plan_id) {
        Plan findPlan = planRepository.findByAppUserIdAndId(app_user_id, plan_id)
                .orElseThrow(() -> new CustomException(ErrorCode.PLAN_NOT_FOUND));
        
        return new PlanOptionDto.Response(findPlan.getPlanOption());
    }
    
    @Transactional
    public void updatePlan(int app_user_id, int plan_id, PlanOptionDto.Request dto) {
        List<Plan> findPlans = planRepository.findAllByAppUserId(app_user_id);
        
        if (findPlans.isEmpty())
            throw new CustomException(ErrorCode.APP_USER_NOT_FOUND);
        
        Plan findPlan = findPlans.stream().filter(plan -> plan.getId() == plan_id)
                .findFirst()
                .orElseThrow(() -> new CustomException(ErrorCode.APP_USER_NOT_FOUND));
        PlanOption findPlanOption = findPlan.getPlanOption();
        findPlanOption.update(dto);
    }
}