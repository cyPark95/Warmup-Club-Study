package com.group.miniproject.domain.team.service;

import com.group.miniproject.domain.employee.entity.Employee;
import com.group.miniproject.domain.employee.entity.EmployeeRole;
import com.group.miniproject.domain.team.dto.request.TeamRegisterRequest;
import com.group.miniproject.domain.team.dto.response.TeamInfoResponse;
import com.group.miniproject.domain.team.entity.Team;
import com.group.miniproject.domain.team.repository.TeamRepository;
import com.group.miniproject.global.exception.ApiException;
import com.group.miniproject.util.EmployeeFixtureFactory;
import com.group.miniproject.util.TeamFixtureFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("팀 Service 검증")
@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

    @InjectMocks
    private TeamService teamService;

    @Mock
    private TeamRepository teamRepository;

    @DisplayName("팀 저장 성공")
    @Test
    void saveTeam() {
        // given
        TeamRegisterRequest request = TeamFixtureFactory.createTeamRegisterRequest();

        // when
        teamService.saveTeam(request);

        // then
        verify(teamRepository, atLeastOnce()).save(any(Team.class));
    }

    @DisplayName("팀 이름으로 조회 성공")
    @Test
    void findTeamByName() {
        // given
        Team team = TeamFixtureFactory.createTeam();

        when(teamRepository.findByName(team.getName())).thenReturn(Optional.of(team));

        // when
        Team result = teamService.findTeamByName(team.getName());

        // then
        assertThat(result).isEqualTo(team);
    }

    @DisplayName("없는 팀의 이름으로 조회 시, 예외가 발생")
    @Test
    void findTeamByName_notFoundName() {
        // given
        when(teamRepository.findByName(any(String.class))).thenReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> teamService.findTeamByName("name"))
                .isInstanceOf(ApiException.class);
    }

    @DisplayName("모든 팀 정보 조회 성공")
    @Test
    void findAllTeam() {
        // given
        Team team = TeamFixtureFactory.createTeam();
        Employee employee = EmployeeFixtureFactory.createEmployee(EmployeeRole.MANAGER);
        employee.joinTeam(team);

        when(teamRepository.findAllFetchEmployee()).thenReturn(List.of(team));

        // when
        List<TeamInfoResponse> result = teamService.findAllTeam();

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).name()).isEqualTo(team.getName());
        assertThat(result.get(0).manager()).isEqualTo(employee.getName());
        assertThat(result.get(0).memberCount()).isEqualTo(1);
    }

    @DisplayName("팀 정보 조회 시, 매니저가 없으면 매니저 이름 null 반환")
    @Test
    void findAllTeam_hasNotManager() {
        // given
        Team team = TeamFixtureFactory.createTeam();

        when(teamRepository.findAllFetchEmployee()).thenReturn(List.of(team));

        // when
        List<TeamInfoResponse> result = teamService.findAllTeam();

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).name()).isEqualTo(team.getName());
        assertThat(result.get(0).manager()).isNull();
        assertThat(result.get(0).memberCount()).isEqualTo(0);
    }
}
