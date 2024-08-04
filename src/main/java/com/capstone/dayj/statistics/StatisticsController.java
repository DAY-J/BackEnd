package com.capstone.dayj.statistics;

import com.capstone.dayj.tag.Tag;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/app-user/{app_user_id}")
public class StatisticsController {
    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("statistics/{start_date}/{end_date}")
    public List<Map<LocalDate, Long>> getOverallStatistics(@PathVariable int app_user_id,
                                                           @PathVariable LocalDate start_date, @PathVariable LocalDate end_date,
                                                           @RequestParam(name = "tag", required = false) Tag tag) {
        return statisticsService.calculateOverall(app_user_id, start_date, end_date, tag);
    }
}
