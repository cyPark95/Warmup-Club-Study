package com.group.libraryapp.dto.Calculator.request;

// DTO(Data Transfer Object)
// 정보 전달 역할을 하는 객체
public record CalculatorAddRequest(
        int number1,
        int number2
) {
}
