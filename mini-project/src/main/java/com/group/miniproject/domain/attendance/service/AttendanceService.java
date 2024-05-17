package com.group.miniproject.domain.attendance.service;

import com.group.miniproject.domain.attendance.dto.request.AttendanceClockInRequest;
import com.group.miniproject.domain.attendance.entity.Attendance;
import com.group.miniproject.domain.attendance.repository.AttendanceRepository;
import com.group.miniproject.domain.employee.entity.Employee;
import com.group.miniproject.domain.employee.service.EmployeeService;
import com.group.miniproject.global.exception.ApiException;
import com.group.miniproject.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EmployeeService employeeService;

    @Transactional
    public void recordClockIn(AttendanceClockInRequest request) {
        Employee employee = employeeService.getEmployee(request.employeeId());
        log.debug("Find Employee: {}", employee);

        LocalDateTime startDateTime = LocalDate.now().atStartOfDay();
        LocalDateTime endDateTime = startDateTime.plusDays(1).minusNanos(1);
        boolean existClockIn = attendanceRepository.findByEmployeeAndClockInBetween(employee, startDateTime, endDateTime)
                .isPresent();

        if (existClockIn) {
            throw new ApiException("ID[%d] 직원은 이미 출근한 기록이 있습니다.", ExceptionCode.ATTENDANCE_ALREADY_CLOCK_IN);
        }

        Attendance attendance = new Attendance(employee);
        attendance.clockIn();

        log.debug("Save Clock In Attendance: {}", attendance);
        attendanceRepository.save(attendance);
    }

}
