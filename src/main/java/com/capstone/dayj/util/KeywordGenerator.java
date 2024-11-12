package com.capstone.dayj.util;

import com.capstone.dayj.plan.Plan;
import com.capstone.dayj.plan.PlanRepository;
import com.capstone.dayj.tag.Tag;
import jakarta.annotation.PostConstruct;
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@Getter
@RequiredArgsConstructor
public class KeywordGenerator {
    private final PlanRepository planRepository;
    private final Komoran komoran = new Komoran(DEFAULT_MODEL.FULL); // Tokenizer
    private final Map<Tag, Set<String>> keywords = new HashMap<Tag, Set<String>>();
    
    @PostConstruct
    @Scheduled(cron = "0 0 0 * * *") // 매일 자정 keyword 갱신
    public void init() {
        LocalDate beforeThreeMonth = LocalDate.now().minusMonths(3); // 오늘로부터 3개월 전의 날짜
        List<Plan> findPlans = planRepository.findAll()
                .stream()
                .filter(plan -> plan.getPlanOption() // 3개월 이내의 계획만 필터링 (트렌드 반영을 위한 날짜 제한)
                        .getPlanStartTime().toLocalDate()
                        .isAfter(beforeThreeMonth))
                .toList();
        
        for (Tag tag : Tag.values()) { // 모든 태그에 대해 키워드 생성
            List<Plan> tagPlans = findPlans
                    .stream()
                    .filter(plan -> plan.getPlanTag().equals(tag)) // 3개월 이내의 계획중 각 태그에 맞는 계획 추출
                    .toList();
            
            if (tagPlans.isEmpty()) {
                keywords.put(tag, null);
            }
            else {
                Map<String, Integer> cnt = new HashMap<>(Map.of()); // {단어, 개수}
                tagPlans.forEach(plan -> {
                    komoran.analyze(plan.getGoal())
                            .getNouns() // 계획 제목으로부터 명사만 추출 => 명사가 곧 keyword
                            .forEach(keyword -> { // 키워드의 빈도수를 counting => 유저들의 계획 트렌드를 반영
                                if (cnt.containsKey(keyword)) {
                                    cnt.replace(keyword, cnt.get(keyword) + 1);
                                }
                                else {
                                    cnt.put(keyword, 1);
                                }
                            });
                });
                
                keywords.put(tag, cnt.entrySet()
                        .stream()
                        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue())) // 빈도수의 내림차순으로 정렬
                        .limit(10) // 키워드가 너무 많으면 트렌드가 아니어도 리마인더 리스트에 포함될 수 있으므로 10개로 제한하여 트렌드 반영
                        .map(Map.Entry::getKey) // 빈도는 제외하고 키워드만 저장
                        .collect(Collectors.toSet()));
            }
        }
        
        log.info("========================= keyword generator ========================");
        keywords.forEach((key, value) -> log.info(String.format("TAG : %s - %s", key, value)));
        log.info("===================== keyword generate complete ====================");
    }
}
