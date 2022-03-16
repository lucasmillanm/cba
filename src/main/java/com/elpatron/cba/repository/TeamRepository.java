package com.elpatron.cba.repository;

import com.elpatron.cba.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    @Query("select s from Team s where s.teamName = ?1")
    Optional<Team> findTeamByName(String teamName);
    Optional<Team> findTeamByTeamID(Long id);
}
