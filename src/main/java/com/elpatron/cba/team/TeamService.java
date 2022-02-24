package com.elpatron.cba.team;

import com.elpatron.cba.team.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TeamService {
    private final TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public void addNewTeam(Team team) {
        Optional<Team> teamOptional = teamRepository.findTeamByName(team.getTeamName());
        if (teamOptional.isPresent()) {
            throw new IllegalStateException("team exists");
        }
        teamRepository.save(team);
    }

    private boolean notEmpty(String name) {
        return name != null && name.length() > 0;
    }

    @Transactional
    public void updateTeam(Long teamID, String teamName) {
        Team team = teamRepository.findById(teamID)
                .orElseThrow(() -> new IllegalStateException(
                        "player with id " + teamID + " does not exist"
                ));
        if (notEmpty(teamName) &&
                !Objects.equals(team.getTeamName(), teamName)) {
            team.setTeamName(teamName);
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
