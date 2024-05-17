package com.group.miniproject.domain.attendance.service;

import com.group.miniproject.domain.attendance.dto.request.AttendanceClockInRequest;
import com.group.miniproject.domain.attendance.entity.Attendance;
import com.group.miniproject.domain.attendance.repository.AttendanceRepository;
import com.group.miniproject.domain.employee.entity.Employee;
import com.group.miniproject.domain.employee.entity.EmployeeRole;
import com.group.miniproject.domain.employee.service.EmployeeService;
import com.group.miniproject.global.exception.ApiException;
import com.group.miniproject.util.EmployeeFixtureFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@DisplayName("출퇴근 Service 검증")
@ExtendWith(MockitoExtension.class)
class AttendanceServiceTest {

    @InjectMocks
    private AttendanceService attendanceService;

    @Mock
    private AttendanceRepository attendanceRepository;

    @Mock
    private EmployeeService employeeService;

    @DisplayName("출근 기록 성공")
    @Test
    void recordClockIn() {
        // given
        Long employeeId = -1L;
        AttendanceClockInRequest request = new AttendanceClockInRequest(employeeId);
        Employee employee = EmployeeFixtureFactory.createEmployee(EmployeeRole.MEMBER);

        when(employeeService.getEmployee(employeeId)).thenReturn(employee);
        when(attendanceRepository.findByEmployeeAndClockInBetween(eq(employee), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Optional.empty());

        // when
        attendanceService.recordClockIn(request);

        // then
        verify(attendanceRepository).save(any(Attendance.class));
    }

    @DisplayName("이미 출근 기록이 있는 경우 예외 발생")
    @Test
    void recordClockIn_alreadyClockIn() {
        // given
        Long employeeId = -1L;
        AttendanceClockInRequest request = new AttendanceClockInRequest(employeeId);
        Employee employee = EmployeeFixtureFactory.createEmployee(EmployeeRole.MEMBER);

        when(employeeService.getEmployee(employeeId)).thenReturn(employee);
        when(attendanceRepository.findByEmployeeAndClockInBetween(eq(employee), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Optional.of(new Attendance(employee)));

        // when
        // then
        assertThatThrownBy(() -> attendanceService.recordClockIn(request))
                .isInstanceOf(ApiException.class);
    }
}
