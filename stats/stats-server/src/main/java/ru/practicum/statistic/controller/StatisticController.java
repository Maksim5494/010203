package ru.practicum.statistic.controller;

import ru.practicum.dto.StatisticDto;
import ru.practicum.dto.StatisticResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import ru.practicum.statistic.service.StatisticService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hibernate.validator.internal.engine.messageinterpolation.el.RootResolver.FORMATTER;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StatisticController {
    private final StatisticService statisticService;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public StatisticDto addInStats(@Valid @RequestBody StatisticDto statisticDto) {
        log.info("StatisticController, addInStats, Request body app: {}, uri: {}, ip: {}, timestamp: {}",
                statisticDto.getApp(), statisticDto.getUri(), statisticDto.getIp(), statisticDto.getTimestamp());
        return statisticService.addToStats(statisticDto);
    }

    @GetMapping("/stats")
    public List<StatisticResponse> getStats(@RequestParam("start") String start,
                                            @RequestParam("end") String end,
                                            @RequestParam(required = false, value = "uris") List<String> uris,
                                            @RequestParam(required = false, value = "unique", defaultValue = "false") boolean unique) {
        log.info("Statistic Controller, getStats, parameters: start {}, end {}, uris {}, unique {}",
                start, end, uris, unique);

        LocalDateTime startDateTime = LocalDateTime.parse(start, FORMATTER);
        LocalDateTime endDateTime = LocalDateTime.parse(end, FORMATTER);

        return statisticService.getStats(startDateTime, endDateTime, uris, unique);
    }
}