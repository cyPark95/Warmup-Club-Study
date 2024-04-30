package com.group.mission.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalculationService {

    /**
     * 두 개의 숫자 더하기
     */
    public int addTwoNumber(int num1, int num2) {
        return num1 + num2;
    }

    /**
     * 두 개의 숫자 빼기
     */
    public int minusTwoNumber(int num1, int num2) {
        return num1 - num2;
    }

    /**
     * 두 개의 숫자 곱하기
     */
    public int multiplyTwoNumber(int num1, int num2) {
        return num1 * num2;
    }

    /**
     * 숫자 목록 더하기
     */
    public int sumOfNumbers(List<Integer> numbers) {
        return numbers.stream()
                .mapToInt(i -> i)
                .sum();
    }
}
