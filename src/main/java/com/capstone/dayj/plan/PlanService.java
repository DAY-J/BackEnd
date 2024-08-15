package com.capstone.dayj.plan;

import com.capstone.dayj.appUser.AppUser;
import com.capstone.dayj.appUser.AppUserRepository;
import com.capstone.dayj.exception.CustomException;
import com.capstone.dayj.exception.ErrorCode;
import com.capstone.dayj.planOption.PlanOption;
import com.capstone.dayj.planOption.PlanOptionDto;
import com.capstone.dayj.planOption.PlanOptionRepository;
import com.capstone.dayj.tag.Tag;
import com.capstone.dayj.util.ImageUploader;
import com.capstone.dayj.util.KeywordGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlanService {
    private final PlanRepository planRepository;
    private final PlanOptionRepository planOptionRepository;
    private final AppUserRepository appUserRepository;
    private final KeywordGenerator keywordGenerator;
    private final ImageUploader imageUploader;
    
    
    @Transactional
    public PlanDto.Response createPlan(int app_user_id, PlanDto.Request planDto, PlanOptionDto.Request planOptionDto) {
        AppUser findAppUser = appUserRepository.findById(app_user_id)
                .orElseThrow(() -> new CustomException(ErrorCode.APP_USER_NOT_FOUND));
        
        planDto.setAppUser(findAppUser);
        Plan savedPlan = planRepository.save(planDto.toEntity());
        
        planOptionDto.setPlan(savedPlan);
        PlanOption savedPlanOption = planOptionRepository.save(planOptionDto.toEntity());
        savedPlan.update(PlanDto.Request.builder()
                .planOption(savedPlanOption)
                .build());

        createRepeatedPlan(planDto, planOptionDto);

        return new PlanDto.Response(savedPlan);
    }
    
    @Transactional(readOnly = true)
    public List<PlanDto.Response> readAllPlanByDate(int app_user_id, LocalDate date) {
        List<Plan> findPlans;
        
        if (date == null) {
            findPlans = planRepository.findAll();
        }
        else {
            findPlans = planRepository.findAllByAppUserId(app_user_id).stream()
                    .filter(plan -> plan.getPlanOption().getPlanStartTime().toLocalDate().equals(date))
                    .toList();
        }
        
        if (findPlans.isEmpty())
            throw new CustomException(ErrorCode.PLAN_NOT_FOUND);
        
        return findPlans.stream().map(PlanDto.Response::new).collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<PlanDto.Response> readAllPlanByPlanTag(int app_user_id, Tag plan_tag, LocalDate date) {
        List<Plan> findPlans = planRepository.findAllByAppUserIdAndPlanTag(app_user_id, plan_tag).stream()
                .filter(plan -> plan.getPlanOption().getPlanStartTime().toLocalDate().equals(date))
                .toList();
        
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
    
    @Transactional()
    public Set<String> recommendPlan(int app_user_id, Tag tag) {
        List<Plan> findPlans = planRepository.findAllByAppUserId(app_user_id).stream()
                .filter(plan -> plan.getPlanTag().equals(tag)).toList();
        Set<String> recommendGoal = new HashSet<>(Set.of());
        
        if (findPlans.isEmpty()) {
            throw new CustomException(ErrorCode.PLAN_NOT_FOUND);
        }
        
        keywordGenerator.getKeywords().get(tag)
                .forEach(keyword -> {
                    findPlans.forEach(plan -> {
                        if (plan.getGoal().contains(keyword))
                            recommendGoal.add(plan.getGoal());
                    });
                });
        
        if (recommendGoal.isEmpty()) {
            recommendGoal.addAll(keywordGenerator.getKeywords().get(tag));
        }
        
        return recommendGoal
                .stream()
                .limit(3)
                .collect(Collectors.toSet());
    }
    
    @Transactional
    public PlanDto.Response patchPlan(int plan_id, PlanDto.Request planDto, PlanOptionDto.Request planOptionDto) {
        Plan findPlan = planRepository.findById(plan_id)
                .orElseThrow(() -> new CustomException(ErrorCode.PLAN_NOT_FOUND));
        findPlan.getPlanOption().update(planOptionDto);
        findPlan.update(planDto);

        createRepeatedPlan(planDto, planOptionDto);

        return new PlanDto.Response(findPlan);
    }
    
    @Transactional
    public PlanDto.Response patchPlanImage(int plan_id, MultipartFile image) throws IOException {
        Plan findPlan = planRepository.findById(plan_id)
                .orElseThrow(() -> new CustomException(ErrorCode.PLAN_NOT_FOUND));
        
        PlanDto.Request dto = new PlanDto.Request();
        dto.setPlanPhoto(imageUploader.upload(image));
        findPlan.update(dto);
        return new PlanDto.Response(findPlan);
    }
    
    @Transactional
    public String deletePlanById(int plan_id) {
        Plan findPlan = planRepository.findById(plan_id)
                .orElseThrow(() -> new CustomException(ErrorCode.PLAN_NOT_FOUND));
        planRepository.delete(findPlan);
        return String.format("Plan(id: %d) was Deleted", findPlan.getId());
    }

    public void createRepeatedPlan(PlanDto.Request planDto, PlanOptionDto.Request planOptionDto){
        if (planOptionDto.getPlanRepeatStartDate()!= null){
            LocalDate startDate = planOptionDto.getPlanRepeatStartDate().toLocalDate();
            LocalDate endDate = planOptionDto.getPlanRepeatEndDate().toLocalDate();
            if(startDate.isAfter(endDate)) throw new CustomException(ErrorCode.DATE_RANGE_ERROR);

            for(LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)){
                if(planOptionDto.getPlanDaysOfWeek().contains(date.getDayOfWeek())){

                    Plan savedPlan = planRepository.save(planDto.toEntity());
                    
                    PlanOptionDto.Request newPlanOptionDto = PlanOptionDto.Request.builder()
                            .plan(savedPlan)
                            .planStartTime(date.atStartOfDay())
                            .planEndTime(date.atTime(1, 0, 0))
                            .build();

                    PlanOption savedPlanOption = planOptionRepository.save(newPlanOptionDto.toEntity());

                    savedPlan.update(PlanDto.Request.builder()
                            .planOption(savedPlanOption)
                            .build());
                }
            }
        }
    }
}