package com.group.miniproject.domain.team.service;

import com.group.miniproject.domain.team.dto.request.TeamRegisterRequest;
import com.group.miniproject.domain.team.entity.Team;
import com.group.miniproject.domain.team.repository.TeamRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

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
}
