package com.group.miniproject.domain.team.service;

import com.group.miniproject.domain.team.dto.request.TeamRegisterRequest;
import com.group.miniproject.domain.team.entity.Team;
import com.group.miniproject.domain.team.repository.TeamRepository;
import com.group.miniproject.util.TeamFixtureFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        TeamRegisterRequest request = new TeamRegisterRequest("team_name");

        // when
        teamService.saveTeam(request);

        // then
        verify(teamRepository, atLeastOnce()).save(any(Team.class));
    }

    @DisplayName("팀 이름으로 조회 실패 - 존재하지 않는 팀 이름")
    @Test
    void getTeamByName_notFoundName() {
        // given
        when(teamRepository.findByName(any(String.class))).thenReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> teamService.getTeamByName("name"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("팀 이름으로 조회 성공")
    @Test
    void getTeamByName() {
        // given
        Team team = TeamFixtureFactory.createTeam();

        when(teamRepository.findByName(team.getName())).thenReturn(Optional.of(team));

        // when
        Team result = teamService.getTeamByName(team.getName());

        // then
        assertThat(result).isEqualTo(team);
    }
}
