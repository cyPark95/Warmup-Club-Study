package com.group.miniproject.domain.team.service;

import com.group.miniproject.domain.team.dto.request.TeamRegisterRequest;
import com.group.miniproject.domain.team.dto.response.TeamResponse;
import com.group.miniproject.domain.team.entity.Team;
import com.group.miniproject.domain.team.repository.TeamRepository;
import com.group.miniproject.global.exception.ApiException;
import com.group.miniproject.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class TeamService {

    private final TeamRepository teamRepository;

    @Transactional
    public void saveTeam(TeamRegisterRequest request) {
        log.debug("Request Data: {}", request);

        Team team = request.toTeam();

        log.debug("Save Team: {}", team);
        teamRepository.save(team);
    }

    public Team findTeamByName(String name) {
        log.debug("Find Team Name: {}", name);
        return teamRepository.findByName(name)
                .orElseThrow(() -> new ApiException(String.format("존재하지 않는 팀 이름[%s] 입니다.", name), ExceptionCode.NOT_FOUND_ENTITY));
    }

    public List<TeamResponse> findAllTeam() {
        return teamRepository.findAllFetchEmployee().stream()
                .map(TeamResponse::from)
                .toList();
    }
}
