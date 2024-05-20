package com.group.miniproject.presentation;

import com.group.miniproject.domain.team.dto.request.TeamRegisterRequest;
import com.group.miniproject.domain.team.dto.response.TeamResponse;
import com.group.miniproject.domain.team.service.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/teams")
public class TeamController {

    private final TeamService teamService;

    @PostMapping
    public void registerTeam(@RequestBody @Valid TeamRegisterRequest request) {
        teamService.saveTeam(request);
    }

    @GetMapping
    public List<TeamResponse> getAllTeam() {
        return teamService.findAllTeam();
    }
}
