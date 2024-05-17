package com.group.miniproject.domain.attendance.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group.miniproject.domain.attendance.dto.request.AttendanceClockInRequest;
import com.group.miniproject.domain.employee.entity.Employee;
import com.group.miniproject.domain.employee.entity.EmployeeRole;
import com.group.miniproject.domain.employee.repository.EmployeeRepository;
import com.group.miniproject.util.EmployeeFixtureFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("출퇴근 Controller 검증")
@SpringBootTest
@AutoConfigureMockMvc
class AttendanceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    @DisplayName("출근 기록 성공")
    @Test
    void clockIn() throws Exception {
        // given
        Employee employee = EmployeeFixtureFactory.createEmployee(EmployeeRole.MEMBER);
        employeeRepository.save(employee);

        AttendanceClockInRequest request = new AttendanceClockInRequest(employee.getId());

        // when
        // then
        mockMvc.perform(post("/api/v1/attendance/clock-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("출근 기록 시, 유효하지 않은 Parameter인 경우 예외 발생")
    @ParameterizedTest
    @NullSource
    void clockIn_invalidParameter(Long employeeId) throws Exception {
        // given
        AttendanceClockInRequest request = new AttendanceClockInRequest(employeeId);

        // when
        // then
        mockMvc.perform(post("/api/v1/attendance/clock-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
