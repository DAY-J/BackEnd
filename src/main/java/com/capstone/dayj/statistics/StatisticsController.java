package com.capstone.dayj.statistics;

import com.capstone.dayj.tag.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("statistics-all/{startDate}/{endDate}")
    public List<Map<LocalDate, Integer>> getOverallStatistics(@PathVariable int app_user_id, @PathVariable LocalDate startDate, @PathVariable LocalDate endDate){
        return statisticsService.calculateOverall(app_user_id, startDate, endDate);
    }

    @GetMapping("statistics-tag/{startDate}/{endDate}/{tag}")
    public List<Map<LocalDate, Integer>> getTagStatistics(@PathVariable int app_user_id, @PathVariable LocalDate startDate, @PathVariable LocalDate endDate, @PathVariable Tag tag){
        return statisticsService.calculateTag(app_user_id, startDate, endDate, tag);
    }
}
