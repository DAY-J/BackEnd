package com.capstone.dayj.planOption;

import com.capstone.dayj.appUser.AppUser;
import com.capstone.dayj.appUser.AppUserRepository;
import com.capstone.dayj.exception.CustomException;
import com.capstone.dayj.exception.ErrorCode;
import com.capstone.dayj.plan.Plan;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlanOptionService {
    private final PlanOptionRepository planRepository;
    private final AppUserRepository appUserRepository;
    
    @Transactional
    public List<PlanOptionDto.Response> readAllPlanDate(int user_id, int plan_id) {
        AppUser findAppUser = appUserRepository.findById(user_id)
                .orElseThrow(() -> new CustomException(ErrorCode.APP_USER_NOT_FOUND));
        List<Plan> plans = planRepository.findAllByAppUser(findAppUser);
        
        if (plans.isEmpty())
            throw new CustomException(ErrorCode.PLAN_NOT_FOUND);
        
        List<PlanOption> planOptions = plans.stream()
                .map(Plan::getPlanOption)
                .toList();
        
        return planOptions.stream().map(PlanOptionDto.Response::new).collect(Collectors.toList());
    }
    
    @Transactional
    public void updatePlan(int user_id, int plan_id, PlanOptionDto.Request dto) {
        AppUser findAppUser = appUserRepository.findById(user_id)
                .orElseThrow(() -> new CustomException(ErrorCode.APP_USER_NOT_FOUND));
        List<Plan> plans = planRepository.findAllByAppUser(findAppUser);
        
        if (plans.isEmpty())
            throw new CustomException(ErrorCode.PLAN_NOT_FOUND);
        
        Plan findPlan = plans.stream().filter(plan -> plan.getId() == plan_id)
                .findFirst()
                .orElseThrow(() -> new CustomException(ErrorCode.APP_USER_NOT_FOUND));
        PlanOption findPlanOption = findPlan.getPlanOption();
        
        findPlanOption.update(dto.getPlanAlarmTime(), dto.getPlanStartTime(), dto.getPlanEndTime(),
                dto.getPlanRepeatStartDate(), dto.getPlanRepeatEndDate(), dto.getPlanDaysOfWeek());
    }
}