package com.group.miniproject.domain.team.dto.request;

import com.group.miniproject.domain.team.entity.Team;
import jakarta.validation.constraints.NotBlank;

public record TeamRegisterRequest(
        String name
) {

    public Team toTeam() {
        return Team.builder()
                .name(this.name)
                .build();
    }
}
