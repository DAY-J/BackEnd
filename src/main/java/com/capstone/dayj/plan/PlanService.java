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
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import lombok.RequiredArgsConstructor;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlanService {
    private final PlanRepository planRepository;
    private final PlanOptionRepository planOptionRepository;
    private final AppUserRepository appUserRepository;
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
    public List<String> recommendPlan(int app_user_id, Tag tag) {
        List<Plan> findPlans = planRepository.findAllByAppUserId(app_user_id).stream()
                .filter(plan -> plan.getPlanTag().equals(tag)).toList();
        
        // 만약 계획이 하나도 없는 경우 추천 못하게 안내 띄워주기 -> 프론트
        if (findPlans.isEmpty()) {
            throw new CustomException(ErrorCode.PLAN_NOT_FOUND);
        }

        /*
                    String word = keywordExtractor.extractKeyword(plan.getGoal(), true)
                    .stream()
                    .map(Token::getString)
                    .max((s1, s2) -> Integer.compare(s1.length(), s2.length()))
                    .orElse(null);
            
            if (cnt.containsKey(word)) {
                cnt.replace(word, cnt.get(word) + 1);
            }
            else {
                cnt.put(word, 1);
            }
         */

//        String match = "[^가-힣a-zA-Z ]"; // 한글, 영어 빼고 전부 제거하는 정규식
        Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);
        KeywordExtractor keywordExtractor = new KeywordExtractor();
        Map<String, Integer> cnt = new HashMap<String, Integer>(Map.of()); // {단어, 개수}
        findPlans.forEach(plan -> {
            komoran.analyze(plan.getGoal()).getNouns()
                    .forEach(keyword -> {
                        if (cnt.containsKey(keyword)) {
                            cnt.replace(keyword, cnt.get(keyword) + 1);
                        }
                        else {
                            cnt.put(keyword, 1);
                        }
                    });
        });
        
        return cnt.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .map(Map.Entry::getKey)
                .limit(3)
                .toList();
    }
    
    @Transactional
    public PlanDto.Response patchPlan(int plan_id, PlanDto.Request planDto, PlanOptionDto.Request planOptionDto) {
        Plan findPlan = planRepository.findById(plan_id)
                .orElseThrow(() -> new CustomException(ErrorCode.PLAN_NOT_FOUND));
        findPlan.getPlanOption().update(planOptionDto);
        findPlan.update(planDto);
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
    public void deletePlanById(int plan_id) {
        Plan findPlan = planRepository.findById(plan_id)
                .orElseThrow(() -> new CustomException(ErrorCode.PLAN_NOT_FOUND));
        planRepository.delete(findPlan);
    }
}