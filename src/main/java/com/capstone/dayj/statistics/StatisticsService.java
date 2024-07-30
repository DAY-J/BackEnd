package com.capstone.dayj.statistics;

import com.capstone.dayj.plan.Plan;
import com.capstone.dayj.plan.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final PlanRepository planRepository;
    private final StatisticsRepository statisticsRepository;

    @Transactional
    public StatisticsDto.Response calculateStatistics(StatisticsDto.Request request) {
        int userId = request.getAppUser().getId();
        String tag = request.getTag();
        LocalDate date = request.getDate();

        List<Plan> plans = planRepository.findAllByAppUserIdAndTagAndDate(userId, tag, date);

        int totalGoals = plans.size();
        int achievedGoals = (int) plans.stream().filter(Plan::isComplete).count();

        double achievementPercentage;

        if (totalGoals == 0) {
            achievementPercentage = 0;
        }
        else {
            achievementPercentage = ((double) achievedGoals / totalGoals) * 100;
        }

        Statistics statistics = request.toEntity();
        statistics.updatePercentage(achievementPercentage);
        statistics = statisticsRepository.save(statistics);

        return new StatisticsDto.Response(statistics);
    }

    public StatisticsDto.Response getStatistics(int id) {
        Statistics statistics = statisticsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Statistics not found with id " + id));
        return new StatisticsDto.Response(statistics);
    }

    // 특정 사용자(userId)의 통계 데이터를 지정된 날짜 범위(startDate ~ endDate)로 조회하여 반환
    public List<StatisticsDto.Response> getStatisticsByDateRange(int userId, LocalDate startDate, LocalDate endDate) {
        List<Statistics> statisticsList = statisticsRepository.findAllByAppUserIdAndDateBetween(userId, startDate, endDate);
        return statisticsList.stream().map(StatisticsDto.Response::new).collect(Collectors.toList());
    }
}
