package com.capstone.dayj.util;

import com.capstone.dayj.plan.Plan;
import com.capstone.dayj.plan.PlanRepository;
import com.capstone.dayj.tag.Tag;
import jakarta.annotation.PostConstruct;
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

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
        for (Tag tag : Tag.values()) { // 모든 태그에 대해 키워드 생성
            List<Plan> findPlans = planRepository.findAll()
                    .stream()
                    .filter(plan -> plan.getPlanTag().equals(tag))
                    .toList();
            
            if (findPlans.isEmpty()) {
                keywords.put(tag, null);
            }
            else {
                Map<String, Integer> cnt = new HashMap<String, Integer>(Map.of()); // {단어, 개수}
                findPlans.forEach(plan -> {
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
                        .map(Map.Entry::getKey) // 빈도는 제외하고 키워드만 저장
                        .collect(Collectors.toSet()));
            }
        }
        
        System.out.println("========================= keyword generator ========================");
        for (var item : keywords.keySet())
            System.out.printf(String.format("key : %s , value : %s\n", item, keywords.get(item)));
        System.out.println("===================== keyword generate complete ====================");
    }
}
