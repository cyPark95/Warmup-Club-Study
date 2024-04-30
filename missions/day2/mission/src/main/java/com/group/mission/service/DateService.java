package com.group.mission.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

@Service
public class DateService {

    /**
     * 날짜에 해당하는 요일 찾기
     */
    public String findDayOfTheWeek(LocalDate date) {
        return date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
    }
}
