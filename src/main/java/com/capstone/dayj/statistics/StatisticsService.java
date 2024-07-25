package com.capstone.dayj.statistics;

import com.capstone.dayj.plan.Plan;
import com.capstone.dayj.plan.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final PlanRepository planRepository;
    private final StatisticsRepository statisticsRepository;

    @Transactional
    public StatisticsDto.Response calculateStatistics(StatisticsDto.Request request) {
        int userId = request.getAppUser().getId();
        String tag = request.getTag();
        LocalDate startDate = request.getStartDate();
        LocalDate endDate = request.getEndDate();

        List<Plan> plans = planRepository.findAllByAppUserIdAndTagAndDateBetween(userId, tag, startDate, endDate);

        int totalGoals = plans.size();
        int achievedGoals = (int) plans.stream().filter(Plan::isComplete).count();

        Statistics statistics = request.toEntity().calculateStatistics(totalGoals, achievedGoals);
        statistics = statisticsRepository.save(statistics);

        return new StatisticsDto.Response(statistics);
    }

    public StatisticsDto.Response getStatistics(int id) {
        Statistics statistics = statisticsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Statistics not found with id " + id));
        return new StatisticsDto.Response(statistics);
    }
}
