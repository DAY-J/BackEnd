package com.capstone.dayj.statistics;

import com.capstone.dayj.appUser.AppUser;
import com.capstone.dayj.appUser.AppUserRepository;
import com.capstone.dayj.exception.CustomException;
import com.capstone.dayj.exception.ErrorCode;
import com.capstone.dayj.plan.PlanDto;
import com.capstone.dayj.tag.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final AppUserRepository appUserRepository;
    private final StatisticsRepository statisticsRepository;


    @Transactional
    public List<Map<LocalDate, Integer>> calculateOverall(int app_user_id, LocalDate startDate, LocalDate endDate) {
        AppUser findAppUser = appUserRepository.findById(app_user_id)
                .orElseThrow(() -> new CustomException(ErrorCode.APP_USER_NOT_FOUND));

        return getDatesInRange(startDate, endDate).stream()
                .map(date -> {
                    List<PlanDto.Response> plansForDate = findAppUser.getPlans().stream()
                            .filter(plan -> plan.getPlanOption().getPlanStartTime().toLocalDate().isEqual(date))
                            .map(PlanDto.Response::new)
                            .collect(Collectors.toList());

                    int achievementRate = calculateAchievementRate(plansForDate);

                    //이게 문제
                    updateOrCreateStatistics(findAppUser, date, achievementRate);

                    return Map.of(date, achievementRate);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public StatisticsDto.Response calculateStatistics(StatisticsDto.Request request) {
        int userId = request.getAppUser().getId();
        String tag = request.getTag();
        LocalDate date = request.getDate();

        List<Plan> plans = planRepository.findAllByAppUserIdAndTagAndDate(userId, tag, date);

        int totalGoals = plans.size();
        int achievedGoals = (int) plans.stream().filter(Plan::getIsComplete).count();

        double achievementPercentage;

        if (totalGoals == 0) {
            achievementPercentage = 0;
        }
        return dates;
    }

    private int calculateAchievementRate(List<PlanDto.Response> plans) {
        int numOfGoal = plans.size();
        long numOfAchievedGoal = plans.stream().filter(PlanDto.Response::isComplete).count();
        return numOfGoal > 0 ? (int) ((numOfAchievedGoal / (double) numOfGoal) * 100) : 0;
    }

    private void updateOrCreateStatistics(AppUser appUser, LocalDate date, int achievementRate) {
        StatisticsDto.Request dto = new StatisticsDto.Request();
        dto.setAchievementRate(Map.of(date, achievementRate));
        dto.setAppUser(appUser);

        statisticsRepository.save(dto.toEntity());
    }
}

//    @Transactional
//    public List<Map<LocalDate, Integer>> calculateOverall(int app_user_id, LocalDate startDate, LocalDate endDate){
//        StatisticsDto.Request dto = new StatisticsDto.Request();
//        List<Map<LocalDate, Integer>> list = new ArrayList<>();
//
//        AppUser findAppUser = appUserRepository.findById(app_user_id)
//                .orElseThrow(()-> new CustomException(ErrorCode.APP_USER_NOT_FOUND));
//        dto.setAppUser(findAppUser);
//
//        for(LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
//            LocalDate calDate = date;
//
//            List<PlanDto.groupResponse> findPlans = findAppUser.getPlans().stream()
//                    .filter(plan -> plan.getPlanOption().getPlanStartTime().toLocalDate().isEqual(calDate))
//                    .map(PlanDto.groupResponse::new).toList();
//
//            putValue(dto, list, calDate, findPlans);
//        }
//        return list;
//    }

//    @Transactional
//    public List<Map<LocalDate, Integer>> calculateTag(int app_user_id, LocalDate startDate, LocalDate endDate, Tag tag){
//        AppUser findAppUser = appUserRepository.findById(app_user_id)
//                .orElseThrow(()-> new CustomException(ErrorCode.APP_USER_NOT_FOUND));
//
//        List<Map<LocalDate, Integer>> list = new ArrayList<>();
//
//        for(LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)){
//            LocalDate calDate = date;
//
//            List<PlanDto.groupResponse> findPlans = findAppUser.getPlans().stream()
//                    .filter(plan -> plan.getPlanOption().getPlanStartTime().toLocalDate().isEqual(calDate) && plan.getPlanTag().equals(tag))
//                    .map(PlanDto.groupResponse::new).toList();
//
//            putValue(list, calDate, findPlans);
//        }
//        return list;
//    }

//    private void putValue(StatisticsDto.Request dto, List<Map<LocalDate, Integer>> list, LocalDate calDate, List<PlanDto.groupResponse> findPlans) {
//        int numOfGoal = findPlans.size();
//        double numOfAchievedGoal = findPlans.stream().filter(PlanDto.groupResponse::isComplete).count();
//
//        int value = numOfGoal > 0 ? (int)((numOfAchievedGoal / numOfGoal) * 100) : 0;
//
//        Map<LocalDate, Integer> map = new HashMap<>();
//        map.put(calDate, value);
//
//        dto.setAchievementRate(map);
//        statisticsRepository.save(dto.toEntity());
//        list.add(map);
//      }

