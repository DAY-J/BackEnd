package com.capstone.dayj.statistics;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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

    @GetMapping("/{range}")
    // 문자열 날짜 형식 -> LocalDate 타입으로 변환. ISO 8601 형식(YYYY-MM-DD) 사용
    public List<StatisticsDto.Response> getStatisticsByDateRange(@PathVariable("app_user_id") int userId,
                                                                 @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                                 @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return statisticsService.getStatisticsByDateRange(userId, startDate, endDate);
    }

}
