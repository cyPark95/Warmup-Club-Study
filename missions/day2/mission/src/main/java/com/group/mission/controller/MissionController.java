package com.group.mission.controller;

import com.group.mission.facade.dto.request.CalculationRequest;
import com.group.mission.facade.dto.response.CalculationResponse;
import com.group.mission.facade.dto.request.SumOfNumbersReqeust;
import com.group.mission.facade.MissionFacade;
import com.group.mission.facade.dto.request.DayOfTheWeekRequest;
import com.group.mission.facade.dto.response.DayOfTheWeekResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class MissionController {

    private final MissionFacade missionFacade;

    public MissionController(MissionFacade missionFacade) {
        this.missionFacade = missionFacade;
    }

    @GetMapping("/calc")
    public CalculationResponse calculate(CalculationRequest request) {
        return missionFacade.calculateAll(request);
    }

    @GetMapping("/day-of-the-week")
    public DayOfTheWeekResponse getDayOfWeek(DayOfTheWeekRequest request) {
        return missionFacade.getDayOfWeek(request);
    }

    @PostMapping("/sum-of-numbers")
    public int getSumOfNumbers(@RequestBody SumOfNumbersReqeust request) {
        return missionFacade.sumOfNumbers(request);
    }
}
