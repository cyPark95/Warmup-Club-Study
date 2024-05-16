package com.group.miniproject.domain.employee.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.group.miniproject.domain.employee.dto.request.EmployeeRegisterRequest;
import com.group.miniproject.domain.employee.dto.response.EmployeeResponse;
import com.group.miniproject.domain.employee.entity.Employee;
import com.group.miniproject.domain.employee.entity.EmployeeRole;
import com.group.miniproject.domain.team.entity.Team;
import com.group.miniproject.domain.team.repository.TeamRepository;
import com.group.miniproject.util.EmployeeFixtureFactory;
import com.group.miniproject.util.TeamFixtureFactory;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @DisplayName("멤버 직원 등록 성공")
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

    @DisplayName("매니저 직원 등록 성공")
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

    @DisplayName("직원 등록 시, 유효하지 않은 Parameter인 경우 예외 발생")
    @ParameterizedTest
    @MethodSource("invalidRegisterParameter")
    void registerEmployee_invalidParameter(EmployeeRegisterRequest request) throws Exception {
        // when
        // then
        mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("이미 매니저가 존재하는 팀에 매니저 직원 등록 시, 예외 발생")
    @Test
    void registerEmployee_hasManager() throws Exception {
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

    @DisplayName("전체 직원 정보 조회")
    @Test
    void getAllEmployee() throws Exception {
        // given
        Team team = TeamFixtureFactory.createTeam();
        List<Employee> employees = IntStream.range(0, 3)
                .mapToObj(i -> saveMemberWithTeam(team))
                .toList();

        // when
        MvcResult result = mockMvc.perform(get("/api/v1/employees"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // then
        List<EmployeeResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(StandardCharsets.UTF_8), new TypeReference<>() {});
        Tuple[] expectedTuples = employees.stream()
                .map(employee -> tuple(employee.getName(), team.getName(), employee.getRole(), employee.getBirthday(), employee.getWorkStartDate()))
                .toArray(Tuple[]::new);

        assertThat(response).extracting("name", "teamName", "role", "birthday", "workStartDate")
                .contains(expectedTuples);
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

    private Employee saveMemberWithTeam(Team team) {
        Employee employee = EmployeeFixtureFactory.createEmployee(EmployeeRole.MEMBER);
        employee.joinTeam(team);
        teamRepository.save(team);
        return employee;
    }
}
