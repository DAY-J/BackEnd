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
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Getter
@RequiredArgsConstructor
public class KeywordGenerator {
    private final PlanRepository planRepository;
    private final Komoran komoran = new Komoran(DEFAULT_MODEL.FULL); // Tokenizer
    private final Map<Tag, Set<String>> keywords = new HashMap<Tag, Set<String>>();
    
    @PostConstruct
    @Scheduled(cron = "0 0 0 * * *") // 매일 자정 keyword 갱신
    public void init() {
        for (Tag tag : Tag.values()) {
            List<Plan> findPlans = planRepository.findAll()
                    .stream()
                    .filter(plan -> plan.getPlanTag().equals(tag))
                    .toList();
            
            if (findPlans.isEmpty()) {
                keywords.put(tag, null);
            }
            
            Map<String, Integer> cnt = new HashMap<String, Integer>(Map.of()); // {단어, 개수}
            findPlans.forEach(plan -> {
                komoran.analyze(plan.getGoal())
                        .getNouns()
                        .forEach(keyword -> {
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
                    .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toSet()));
        }
        
        System.out.println("========================= keyword generator ========================");
        for (var item : keywords.keySet())
            System.out.printf(String.format("key : %s , value : %s\n", item, keywords.get(item)));
        System.out.println("===================== keyword generate complete ====================");
    }
}
