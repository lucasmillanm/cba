package com.elpatron.cba.repository;

import com.elpatron.cba.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    boolean existsTeamByTeamName(String teamName);
}
