package com.group.miniproject.domain.employee.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group.miniproject.domain.employee.dto.EmployeeRegisterRequest;
import com.group.miniproject.domain.employee.entity.Employee;
import com.group.miniproject.domain.employee.entity.EmployeeRole;
import com.group.miniproject.domain.team.entity.Team;
import com.group.miniproject.domain.team.repository.TeamRepository;
import com.group.miniproject.util.EmployeeFixtureFactory;
import com.group.miniproject.util.TeamFixtureFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("직원 Controller 검증")
@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TeamRepository teamRepository;

    @DisplayName("멤버 직원 등록")
    @Test
    void registerMemberEmployee() throws Exception {
        // given
        Team team = TeamFixtureFactory.createTeam();
        teamRepository.save(team);

        EmployeeRegisterRequest request = EmployeeFixtureFactory.createEmployeeRegisterRequest(false, team.getName());

        // when
        // then
        mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("매니저 직원 등록")
    @Test
    void registerManagerEmployee() throws Exception {
        // given
        Team team = TeamFixtureFactory.createTeam();
        teamRepository.save(team);

        EmployeeRegisterRequest request = EmployeeFixtureFactory.createEmployeeRegisterRequest(true, team.getName());

        // when
        // then
        mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("직원 등록 실패 - 유효하지 않은 Parameter")
    @ParameterizedTest
    @MethodSource("invalidRegisterParameter")
    void registerEmployee_failInvalidParameter(EmployeeRegisterRequest request) throws Exception {
        // when
        // then
        mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("매니저 직원 등록 실패 - 이미 매니저 존재")
    @Test
    void registerEmployee_haseManager() throws Exception {
        // given
        Team team = TeamFixtureFactory.createTeam();
        Employee manager = EmployeeFixtureFactory.createEmployee(EmployeeRole.MANAGER);
        manager.joinTeam(team);
        teamRepository.save(team);

        EmployeeRegisterRequest request = EmployeeFixtureFactory.createEmployeeRegisterRequest(true, team.getName());

        // when
        // then
        mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    private static Stream<EmployeeRegisterRequest> invalidRegisterParameter() {
        return Stream.of(
                new EmployeeRegisterRequest(null, "team_name", true, LocalDate.now(), LocalDate.of(1995, 10, 7)),
                new EmployeeRegisterRequest("", "team_name", true, LocalDate.now(), LocalDate.of(1995, 10, 7)),
                new EmployeeRegisterRequest("employee_name", null, true, LocalDate.now(), LocalDate.of(1995, 10, 7)),
                new EmployeeRegisterRequest("employee_name", "", true, LocalDate.now(), LocalDate.of(1995, 10, 7)),
                new EmployeeRegisterRequest("employee_name", "team_name", null, LocalDate.now(), LocalDate.of(1995, 10, 7)),
                new EmployeeRegisterRequest("employee_name", "team_name", true, null, LocalDate.of(1995, 10, 7)),
                new EmployeeRegisterRequest("employee_name", "team_name", true, LocalDate.now(), null),
                new EmployeeRegisterRequest("", "", true, LocalDate.now(), LocalDate.of(1995, 10, 7)),
                new EmployeeRegisterRequest("", "team_name", null, LocalDate.now(), LocalDate.of(1995, 10, 7)),
                new EmployeeRegisterRequest("employee_name", "", null, LocalDate.now(), LocalDate.of(1995, 10, 7)),
                new EmployeeRegisterRequest("", "", null, LocalDate.now(), LocalDate.of(1995, 10, 7)),
                new EmployeeRegisterRequest("employee_name", "team_name", true, null, null),
                new EmployeeRegisterRequest("", "team_name", true, null, LocalDate.of(1995, 10, 7)),
                new EmployeeRegisterRequest("employee_name", "", true, null, LocalDate.of(1995, 10, 7)),
                new EmployeeRegisterRequest("", "", true, null, LocalDate.of(1995, 10, 7)),
                new EmployeeRegisterRequest("", "team_name", true, LocalDate.now(), null),
                new EmployeeRegisterRequest("employee_name", "", true, LocalDate.now(), null),
                new EmployeeRegisterRequest("", "", true, LocalDate.now(), null)
        );
    }
}
