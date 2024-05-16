package com.group.miniproject.domain.team.repository;

import com.group.miniproject.domain.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {

    Optional<Team> findByName(String name);

    @Query("SELECT t FROM Team t LEFT JOIN t.employees")
    List<Team> findAllFetchEmployee();
}
