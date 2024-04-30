package com.group.mission.facade;

import com.group.mission.facade.dto.request.DayOfTheWeekRequest;
import com.group.mission.facade.dto.response.DayOfTheWeekResponse;
import com.group.mission.facade.dto.request.CalculationRequest;
import com.group.mission.facade.dto.response.CalculationResponse;
import com.group.mission.facade.dto.request.SumOfNumbersReqeust;
import com.group.mission.service.CalculationService;
import com.group.mission.service.DateService;
import org.springframework.stereotype.Service;

@Service
public class MissionFacade {

    private final CalculationService calculationService;
    private final DateService dateService;

    public MissionFacade(CalculationService calculationService, DateService dateService) {
        this.calculationService = calculationService;
        this.dateService = dateService;
    }

    public CalculationResponse calculateAll(CalculationRequest request) {
        int add = calculationService.addTwoNumber(request.num1(), request.num2());
        int minus = calculationService.minusTwoNumber(request.num1(), request.num2());
        int multiply = calculationService.multiplyTwoNumber(request.num1(), request.num2());
        return new CalculationResponse(add, minus, multiply);
    }

    public DayOfTheWeekResponse getDayOfWeek(DayOfTheWeekRequest request) {
        String dayOfTheWeek = dateService.findDayOfTheWeek(request.date());
        return new DayOfTheWeekResponse(dayOfTheWeek.toUpperCase());
    }

    public int sumOfNumbers(SumOfNumbersReqeust request) {
        return calculationService.sumOfNumbers(request.numbers());
    }
}
