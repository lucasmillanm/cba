package com.elpatron.cba.player;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    @Query("select s from Player s where s.number = ?1")
    Optional<Player> findPlayerByNumber(Integer number);
}
