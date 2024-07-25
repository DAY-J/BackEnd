package com.capstone.dayj.statistics;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("api/app-user/{app_user_id}/statistics")
public class StatisticsController {
    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/{id}")
    public StatisticsDto.Response getStatistics(@PathVariable int id) {
        return statisticsService.getStatistics(id);
    }


    @PostMapping
    public StatisticsDto.Response calculateStatistics(@RequestBody StatisticsDto.Request request) {
        return statisticsService.calculateStatistics(request);
    }
}
