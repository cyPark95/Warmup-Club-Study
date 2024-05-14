package com.group.miniproject.domain.team.dto.request;

import com.group.miniproject.domain.team.entity.Team;
import jakarta.validation.constraints.NotBlank;

public record TeamRegisterRequest(
        @NotBlank(message = "팀 이름은 null 또는 공백일 수 없습니다.")
        String name
) {

    public Team toTeam() {
        return Team.builder()
                .name(this.name)
                .build();
    }
}
