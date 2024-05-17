package com.group.miniproject.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {

    NOT_FOUND_ENTITY("조회 된 결과가 없습니다.", HttpStatus.BAD_REQUEST),

    TEAM_NAME_INVALID("유효하지 않은 팀 이름입니다.", HttpStatus.BAD_REQUEST),
    TEAM_ALREADY_HAS_MANAGER("이미 매니저가 존재합니다.", HttpStatus.BAD_REQUEST),

    EMPLOYEE_IS_NULL("직원은 필수 값 입니다.", HttpStatus.BAD_REQUEST),
    EMPLOYEE_NAME_INVALID("유효하지 않은 직원 이름입니다.", HttpStatus.BAD_REQUEST),
    EMPLOYEE_ROLE_NULL("직원의 역할은 필수 값 입니다.", HttpStatus.BAD_REQUEST),
    EMPLOYEE_WORK_START_DATE_NULL("직원 입사 일자는 필수 값 입니다.", HttpStatus.BAD_REQUEST),
    EMPLOYEE_BIRTHDAY_NULL("직원 생일은 필수 값 입니다.", HttpStatus.BAD_REQUEST),

    ATTENDANCE_ALREADY_CLOCK_IN("이미 출근한 기록이 있습니다.", HttpStatus.BAD_REQUEST)
    ;

    private final String message;
    private final HttpStatus httpStatus;
}
