package com.group.miniproject.domain.team.service;

import com.group.miniproject.domain.team.dto.request.TeamRegisterRequest;
import com.group.miniproject.domain.team.entity.Team;
import com.group.miniproject.domain.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class TeamService {

    private final TeamRepository teamRepository;

    @Transactional
    public void saveTeam(TeamRegisterRequest request) {
        Team team = request.toTeam();
        teamRepository.save(team);
    }

    public Team getTeamByName(String name) {
        return teamRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException(String.format("존재하지 않는 팀 이름[%s] 입니다.", name)));
    }
}
