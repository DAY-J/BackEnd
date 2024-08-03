package com.capstone.dayj.statistics;

import com.capstone.dayj.appUser.AppUser;
import com.capstone.dayj.appUser.AppUserRepository;
import com.capstone.dayj.exception.CustomException;
import com.capstone.dayj.exception.ErrorCode;
import com.capstone.dayj.plan.PlanDto;
import com.capstone.dayj.tag.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final AppUserRepository appUserRepository;
    private final StatisticsRepository statisticsRepository;

    @Transactional
    public List<Map<LocalDate, Long>> calculateOverall(int app_user_id, LocalDate startDate, LocalDate endDate, Tag tag) {
        AppUser findAppUser = appUserRepository.findById(app_user_id)
                .orElseThrow(() -> new CustomException(ErrorCode.APP_USER_NOT_FOUND));

        return getDatesInRange(startDate, endDate).stream()
                .map(date -> {
                    List<PlanDto.Response> plansForDate = findAppUser.getPlans().stream()
                            .filter(plan -> plan.getPlanOption().getPlanStartTime().toLocalDate().isEqual(date))
                            .map(PlanDto.Response::new)
                            .collect(Collectors.toList());

                    if (tag != null) { // tag가 있으면
                        plansForDate = plansForDate.stream()
                                .filter(plan -> plan.getPlanTag().equals(tag))
                                .collect(Collectors.toList());
                    }

                    long achievementRate = calculateAchievementRate(plansForDate);

                    if (tag == null) { // tag가 없으면 
                        updateOrCreateStatistics(findAppUser, date, achievementRate);
                    }
                    return Map.of(date, achievementRate);
                })
                .collect(Collectors.toList());
    }

    private List<LocalDate> getDatesInRange(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> dates = new ArrayList<>();
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            dates.add(date);
        }
        return dates;
    }

    private long calculateAchievementRate(List<PlanDto.Response> plans) {
        int numOfGoal = plans.size();
        long numOfAchievedGoal = plans.stream().filter(PlanDto.Response::getIsComplete).count();
        return numOfGoal > 0 ? (long) ((numOfAchievedGoal / (double) numOfGoal) * 100) : 0;
    }

    private void updateOrCreateStatistics(AppUser appUser, LocalDate date, long achievementRate) {
        Statistics findStatistics = statisticsRepository.findByAppUserIdAndDate(appUser.getId(), date);

        if (findStatistics != null) {
            findStatistics.update(StatisticsDto.Request.builder()
                    .achievementRate(achievementRate)
                    .build());
        } else {
            StatisticsDto.Request request = StatisticsDto.Request.builder()
                    .appUser(appUser)
                    .date(date)
                    .achievementRate(achievementRate)
                    .build();
            statisticsRepository.save(request.toEntity());
        }
    }
}

