package com.elpatron.cba.repository;

import com.elpatron.cba.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    @Query("select count(s)>0 from Team s where s.teamName = ?1")
    boolean existsTeamByTeamName(String teamName);
}
