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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
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
    @CacheEvict(cacheNames = "plans", allEntries = true)
    public PlanDto.Response createPlan(int app_user_id, PlanDto.Request planDto, PlanOptionDto.Request planOptionDto) {
        AppUser findAppUser = appUserRepository.findById(app_user_id)
                .orElseThrow(() -> new CustomException(ErrorCode.APP_USER_NOT_FOUND));
        
        planDto.setAppUser(findAppUser);
        Plan savedPlan = planRepository.save(planDto.toEntity());
        
        planOptionDto.setPlan(savedPlan);
        PlanOption savedPlanOption = planOptionRepository.save(planOptionDto.toEntity());
        
        savedPlan.update(PlanDto.Request.builder()
                .childId(createRepeatedPlan(planDto, planOptionDto))
                .planOption(savedPlanOption)
                .build());
        
        return new PlanDto.Response(savedPlan);
    }
    
    // 캐시 사용 전
//    @Transactional(readOnly = true)
//    public List<PlanDto.Response> readAllPlanByDate(int app_user_id, LocalDate date) {
//        List<Plan> findPlans;
//
//        if (date == null) {
//            findPlans = planRepository.findAll();
//        }
//        else {
//            findPlans = planRepository.findAllByAppUserId(app_user_id).stream()
//                    .filter(plan -> plan.getPlanOption().getPlanStartTime().toLocalDate().equals(date))
//                    .toList();
//        }
//
//        if (findPlans.isEmpty())
//            throw new CustomException(ErrorCode.PLAN_NOT_FOUND);
//
//        return findPlans.stream().map(PlanDto.Response::new).collect(Collectors.toList());
//    }

//    @Transactional(readOnly = true)
//    public List<PlanDto.Response> readAllPlanByPlanTag(int app_user_id, Tag plan_tag, LocalDate date) {
//        List<Plan> findPlans = planRepository.findAllByAppUserIdAndPlanTag(app_user_id, plan_tag).stream()
//                .filter(plan -> plan.getPlanOption().getPlanStartTime().toLocalDate().equals(date))
//                .toList();
//
//        if (findPlans.isEmpty())
//            throw new CustomException(ErrorCode.PLAN_NOT_FOUND);
//
//        return findPlans.stream().map(PlanDto.Response::new).collect(Collectors.toList());
//    }
    
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "plans", key = "#app_user_id + ':' + #date", unless = "#result == null || #result.isEmpty()")
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
        
        return findPlans.stream().map(PlanDto.Response::new).toList();
    }
    
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "plans", key = "#app_user_id + ':' + #plan_tag + ':' + #date", unless = "#result == null || #result.isEmpty()")
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
    
    
    // 전처리 전
//    @Transactional()
//    public List<String> reminderTest(int app_user_id, Tag tag) {
//        List<Plan> findPlans = planRepository.findAllByAppUserId(app_user_id)
//                .stream()
//                .filter(plan -> plan.getPlanTag().equals(tag))
//                .toList();
//
//        if (findPlans.isEmpty()) { // 계획이 하나도 없는 경우 에러 throw
//            throw new CustomException(ErrorCode.PLAN_NOT_FOUND);
//        }
//
//        Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);
//        Map<String, Integer> cnt = new HashMap<>(Map.of()); // { 단어, 개수 }
//        findPlans.forEach(plan -> {
//            komoran.analyze(plan.getGoal()).getNouns()
//                    .forEach(keyword -> {
//                        if (cnt.containsKey(keyword)) {
//                            cnt.replace(keyword, cnt.get(keyword) + 1);
//                        }
//                        else {
//                            cnt.put(keyword, 1);
//                        }
//                    });
//        });
//
//        return cnt.entrySet().stream()
//                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
//                .map(Map.Entry::getKey)
//                .limit(3)
//                .toList();
//    }
    
    
    @Transactional
    public Set<String> reminderPlan(int app_user_id, Tag tag) {
        List<Plan> findPlans = new ArrayList<>(planRepository.findAllByAppUserId(app_user_id).stream()
                .filter(plan -> plan.getPlanTag().equals(tag)).toList());
        Set<String> recommendGoal = new HashSet<>(Set.of());
        
        if (keywordGenerator.getKeywords().isEmpty()) {
            throw new CustomException(ErrorCode.KEYWORD_NOT_FOUND);
        }
        
        if (findPlans.isEmpty()) {
            throw new CustomException(ErrorCode.PLAN_NOT_FOUND);
        }
        
        Collections.shuffle(findPlans); // 다양한 계획을 보여주기 위해서 셔플
        keywordGenerator.getKeywords().get(tag)
                .forEach(keyword -> { // 키워드 순회
                    findPlans.forEach(plan -> { // 위에서 찾은 유저의 계획 순회
                        if (recommendGoal.size() >= 3) return; // 키워드 포함 계획 상한 => 3개
                        if (plan.getGoal().contains(keyword)) // 키워드를 포함하는 계획이면 리스트에 추가
                            recommendGoal.add(plan.getGoal());
                    });
                });
        
        findPlans.forEach(plan -> { // 키워드를 포함하지 않는 과거 계획도 보여주기 위해 추가
            if (recommendGoal.size() >= 5) return; // 전체적인 반환 리스트 상한 => 5개
            recommendGoal.add(plan.getGoal());
        });
        
        return recommendGoal;
    }
    
    @Transactional
    @CacheEvict(cacheNames = "plans", allEntries = true)
    public PlanDto.Response patchPlan(int plan_id, PlanDto.Request planDto, PlanOptionDto.Request planOptionDto) {
        Plan findPlan = planRepository.findById(plan_id)
                .orElseThrow(() -> new CustomException(ErrorCode.PLAN_NOT_FOUND));
        
        // 기존 plan에 반복 조건이 있는 경우
        if (findPlan.getChildId() != null && !findPlan.getChildId().isEmpty()) {
            LocalDate beforeRepeatStartDate = findPlan.getPlanOption()
                    .getPlanRepeatStartDate()
                    .toLocalDate();
            LocalDate beforeRepeatEndDate = findPlan.getPlanOption()
                    .getPlanRepeatEndDate()
                    .toLocalDate();
            List<DayOfWeek> beforeDaysOfWeek = findPlan.getPlanOption()
                    .getPlanDaysOfWeek();
            
            // 1. 반복 조건 수정 X, PLAN, 알람 시간, 시작 시간, 종료 시간만 수정하는 경우 -> 부모, 자식계획 동일하게 수정
            findPlan.getChildId().removeIf(childId -> {
                Optional<Plan> childPlan = planRepository.findById(childId);
                childPlan.ifPresent(plan -> {
                    plan.update(PlanDto.Request.builder()
                            .goal(planDto.getGoal())
                            .planTag(planDto.getPlanTag())
                            .isPublic(planDto.getIsPublic())
                            .build());
                    LocalDateTime alarmTime = planOptionDto.getPlanAlarmTime() != null
                            ? childPlan.get().getPlanOption().getPlanStartTime().withHour(planOptionDto.getPlanAlarmTime().getHour()).withMinute(planOptionDto.getPlanAlarmTime().getMinute())
                            : null;
                    plan.getPlanOption().update(PlanOptionDto.Request.builder()
                            .planAlarmTime(alarmTime)
                            .planStartTime(plan.getPlanOption().getPlanStartTime().withHour(planOptionDto.getPlanStartTime().getHour()).withMinute(planOptionDto.getPlanStartTime().getMinute()))
                            .planEndTime(plan.getPlanOption().getPlanEndTime().withHour(planOptionDto.getPlanEndTime().getHour()).withMinute(planOptionDto.getPlanEndTime().getMinute()))
                            .build()); // 날짜는 유지, 시간만 변경
                });
                return childPlan.isEmpty(); // childId에 해당하는 PLAN이 없으면 childId 리스트에서 제거
            });
            
            // 2. 반복 조건 삭제하는 경우 -> 자식 plan 일괄 삭제
            if (planOptionDto.getPlanRepeatStartDate() == null) {
                findPlan.getChildId().forEach(planRepository::deleteById);
                findPlan.getChildId().clear();
            }
            
            // 3. 반복 조건 수정 O -> 기존 자식 plan 삭제 후 새로 생성
            else if (!planOptionDto.getPlanRepeatStartDate().toLocalDate().equals(beforeRepeatStartDate)
                    || !planOptionDto.getPlanRepeatEndDate().toLocalDate().equals(beforeRepeatEndDate)
                    || !planOptionDto.getPlanDaysOfWeek().equals(beforeDaysOfWeek)) {
                findPlan.getChildId().forEach(planRepository::deleteById);
                findPlan.update(PlanDto.Request.builder()
                        .childId(createRepeatedPlan(planDto, planOptionDto))
                        .build());
            }
        }
        else { // 반복조건이 없었는데 새로 추가한다면
            findPlan.update(PlanDto.Request.builder()
                    .childId(createRepeatedPlan(planDto, planOptionDto))
                    .build());
        }
        
        findPlan.getPlanOption().update(planOptionDto);
        findPlan.update(planDto);
        
        return new PlanDto.Response(findPlan);
    }
    
    // TODO
//    @Transactional
//    public PlanDto.Response patchPlanImage(int plan_id, MultipartFile image) throws IOException {
//        if (image == null || image.isEmpty()) {
//            throw new CustomException(ErrorCode.IMAGE_UPLOAD_FAIL);
//        }
//
//        Plan findPlan = planRepository.findById(plan_id)
//                .orElseThrow(() -> new CustomException(ErrorCode.PLAN_NOT_FOUND));
//
//        PlanDto.Request dto = new PlanDto.Request();
//        dto.setPlanPhoto(imageUploader.upload(image));
//        findPlan.update(dto);
//        return new PlanDto.Response(findPlan);
//    }
    
    @Transactional
    @CacheEvict(cacheNames = "plans", allEntries = true)
    public String deletePlanById(int plan_id) {
        Plan findPlan = planRepository.findById(plan_id)
                .orElseThrow(() -> new CustomException(ErrorCode.PLAN_NOT_FOUND));
        
        // 부모 plan 삭제 시, 자식 plan 함께 삭제
        if ((!findPlan.getChildId().isEmpty())) {
            findPlan.getChildId().forEach(planRepository::deleteById);
        }
        
        planRepository.delete(findPlan);
        return String.format("Plan(id: %d) was Deleted", findPlan.getId());
    }
    
    @Transactional
    public List<Integer> createRepeatedPlan(PlanDto.Request planDto, PlanOptionDto.Request planOptionDto) {
        List<Integer> childIds = new ArrayList<>();
        
        if (planOptionDto.getPlanRepeatStartDate() != null) {
            LocalDate startDate = planOptionDto.getPlanRepeatStartDate().toLocalDate();
            LocalDate endDate = planOptionDto.getPlanRepeatEndDate().toLocalDate();
            if (startDate.isAfter(endDate)) throw new CustomException(ErrorCode.DATE_RANGE_ERROR);
            if (startDate.isEqual(planOptionDto.getPlanStartTime().toLocalDate()) || startDate.isBefore(planOptionDto.getPlanStartTime().toLocalDate()))
                throw new CustomException(ErrorCode.DATE_RANGE_ERROR);
            
            for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
                if (planOptionDto.getPlanDaysOfWeek().contains(date.getDayOfWeek())) {
                    int startHour = 0, startMinute = 0, endHour = 1, endMinute = 0;
                    
                    if (planOptionDto.getPlanStartTime() != null) {
                        startHour = planOptionDto.getPlanStartTime().getHour();
                        startMinute = planOptionDto.getPlanStartTime().getMinute();
                        endHour = planOptionDto.getPlanEndTime().getHour();
                        endMinute = planOptionDto.getPlanEndTime().getMinute();
                    }
                    LocalDateTime alarmTime = planOptionDto.getPlanAlarmTime() != null
                            ? date.atTime(planOptionDto.getPlanAlarmTime().getHour(), planOptionDto.getPlanAlarmTime().getMinute(), 0)
                            : null;
                    
                    PlanDto.Request newPlanDto = PlanDto.Request.builder()
                            .appUser(planDto.getAppUser())
                            .planTag(planDto.getPlanTag())
                            .goal(planDto.getGoal())
                            .isPublic(planDto.getIsPublic())
                            .build();
                    
                    Plan savedPlan = planRepository.save(newPlanDto.toEntity());
                    
                    PlanOptionDto.Request newPlanOptionDto = PlanOptionDto.Request.builder()
                            .plan(savedPlan)
                            .planAlarmTime(alarmTime)
                            .planStartTime(date.atTime(startHour, startMinute, 0))
                            .planEndTime(date.atTime(endHour, endMinute, 0))
                            .build();
                    
                    PlanOption savedPlanOption = planOptionRepository.save(newPlanOptionDto.toEntity());
                    
                    savedPlan.update(PlanDto.Request.builder()
                            .planOption(savedPlanOption)
                            .build());
                    
                    childIds.add(savedPlan.getId());
                }
            }
        }
        return childIds;
    }
}
