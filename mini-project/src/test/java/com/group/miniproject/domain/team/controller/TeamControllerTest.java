package com.group.miniproject.domain.team.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.group.miniproject.domain.employee.entity.Employee;
import com.group.miniproject.domain.employee.entity.EmployeeRole;
import com.group.miniproject.domain.team.dto.request.TeamRegisterRequest;
import com.group.miniproject.domain.team.dto.response.TeamResponse;
import com.group.miniproject.domain.team.entity.Team;
import com.group.miniproject.domain.team.repository.TeamRepository;
import com.group.miniproject.util.EmployeeFixtureFactory;
import com.group.miniproject.util.TeamFixtureFactory;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("팀 Controller 검증")
@SpringBootTest
@AutoConfigureMockMvc
class TeamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TeamRepository teamRepository;

    @DisplayName("팀 등록 성공")
    @Test
    void registerTeam() throws Exception {
        // given
        TeamRegisterRequest request = new TeamRegisterRequest("name");

        // when
        // then
        mockMvc.perform(post("/api/v1/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("팀 등록 실패 - 유효하지 않은 Parameter")
    @ParameterizedTest
    @NullAndEmptySource
    void registerTeam_invalidParameter(String name) throws Exception {
        // given
        TeamRegisterRequest request = new TeamRegisterRequest(name);

        // when
        // then
        mockMvc.perform(post("/api/v1/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("전체 팀 조회")
    @Test
    void getAllTeam() throws Exception {
        // given
        List<Team> teams = List.of(
                saveTeamWithMembers(EmployeeRole.MANAGER, EmployeeRole.MEMBER, EmployeeRole.MEMBER),
                saveTeamWithMembers(EmployeeRole.MEMBER, EmployeeRole.MEMBER)
        );

        // when
        // then
        MvcResult result = mockMvc.perform(get("/api/v1/teams"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        List<TeamResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(StandardCharsets.UTF_8), new TypeReference<>() {});
        Tuple[] expectedTuples = teams.stream()
                .map(team -> tuple(team.getName(), getManagerName(team), team.getMemberCount()))
                .toArray(Tuple[]::new);

        assertThat(response).extracting("name", "manager", "memberCount")
                .contains(expectedTuples);
    }

    private Team saveTeamWithMembers(EmployeeRole... roles) {
        Team team = TeamFixtureFactory.createTeam();
        Arrays.stream(roles).forEach(role -> {
            Employee member = EmployeeFixtureFactory.createEmployee(role);
            member.joinTeam(team);
        });

        return teamRepository.save(team);
    }

    private String getManagerName(Team team) {
        return team.getManager()
                .map(Employee::getName)
                .orElse(null);
    }
}
