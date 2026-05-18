package ru.practicum.statistic.controller;

import ru.practicum.dto.StatisticDto;
import ru.practicum.dto.StatisticResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import ru.practicum.statistic.service.StatisticService;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StatisticController {
    private final StatisticService statisticService;


    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public StatisticDto addInStats(@Valid @RequestBody StatisticDto statisticDto) {
        log.info("StatisticController, addInStats, Request body app: {}, uri: {}, ip: {}, timestamp: {}",
                statisticDto.getApp(), statisticDto.getUri(), statisticDto.getIp(), statisticDto.getTimestamp());
        return statisticService.addToStats(statisticDto);
    }

    @GetMapping("/stats")
    @ResponseStatus(HttpStatus.OK)
    public List<StatisticResponse> getStats(@RequestParam("start") String start,
                                            @RequestParam("end") String end,
                                            @RequestParam(required = false, value = "uris") List<String> uris,
                                            @RequestParam(required = false, value = "unique", defaultValue = "false") boolean unique) {
        log.info("Statistic Controller, getStats, parameters: start {}, end {}, uris {}, unique {}",
                start, end, uris, unique);
        return statisticService.getStats(start, end, uris, unique);
    }
}