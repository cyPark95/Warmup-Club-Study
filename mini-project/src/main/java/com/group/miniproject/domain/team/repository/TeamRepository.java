package com.group.miniproject.domain.team.repository;

import com.group.miniproject.domain.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
