package com.elpatron.cba.team;

import com.elpatron.cba.player.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.elpatron.cba.utilities.Functions.notEmpty;

@Service
public class TeamService {
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository, PlayerRepository playerRepository) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public void addNewTeam(Team team) {
        Optional<Team> teamOptional = teamRepository.findTeamByName(team.getTeamName());
        if (teamOptional.isPresent()) {
            throw new IllegalStateException("team already exists");
        }
        teamRepository.save(team);
    }

    @Transactional
    public void updateTeam(Long teamID, String teamName, String teamCity, String teamCoach) {
        Team team = teamRepository.findById(teamID)
                .orElseThrow(() -> new IllegalStateException(
                        "player with id " + teamID + " does not exist"
                ));
        if (notEmpty(teamName) &&
                !Objects.equals(team.getTeamName(), teamName)) {
            team.setTeamName(teamName);
        }
        if (notEmpty(teamCity) &&
                !Objects.equals(team.getTeamCity(), teamCity)) {
            team.setTeamCity(teamCity);
        }
        if (notEmpty(teamCoach) &&
                !Objects.equals(team.getTeamCoach(), teamCoach)) {
            team.setTeamCoach(teamCoach);
        }

    }

    public void deleteTeam(Long teamID) {
        boolean exists = teamRepository.existsById(teamID);
        if (!exists) {
            throw new IllegalStateException("team with id " + teamID + " does not exist");
        }
        teamRepository.deleteById(teamID);
    }
}
