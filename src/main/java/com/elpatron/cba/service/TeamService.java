package com.elpatron.cba.service;

import com.elpatron.cba.dto.TeamDTO;
import com.elpatron.cba.exception.BadRequestException;
import com.elpatron.cba.exception.NotFoundException;
import com.elpatron.cba.model.Player;
import com.elpatron.cba.repository.PlayerRepository;
import com.elpatron.cba.repository.TeamRepository;
import com.elpatron.cba.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.elpatron.cba.utilities.Checkers.checkIsPresent;

@Service
public class TeamService {
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository, PlayerRepository playerRepository) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
    }

    public List<TeamDTO> getAllTeams() {
        return teamRepository.findAll()
                .stream()
                .map(this::teamDTO)
                .collect(Collectors.toList());
    }

    public Team getTeamDetails(Long teamID) {
        Optional<Team> teamOptional = teamRepository.findTeamByTeamID(teamID);
        if (!teamOptional.isPresent()) {
            throw new NotFoundException("team with id " + teamID + " not found");
        }
        return teamOptional.get();
    }

    private TeamDTO teamDTO(Team team) {
        return new TeamDTO(team.getTeamID(), team.getTeamCity(), team.getTeamName(), team.getTeamCoach());
    }

    public void addNewTeam(Team team) {
        Optional<Team> teamOptional = teamRepository.findTeamByName(team.getTeamName());
        if (teamOptional.isPresent()) {
            throw new BadRequestException("team already exists");
        }
        teamRepository.save(team);
    }

    @Transactional
    public void updateTeam(Long teamID, String teamCity, String teamName, String teamCoach) {
        Team team = teamRepository.findById(teamID)
                .orElseThrow(() -> new NotFoundException(
                        "team with id " + teamID + " does not exist"
                ));
        if (!Objects.equals(team.getTeamCity(), teamCity)) {
            team.setTeamCity(teamCity);
        }
        if (!Objects.equals(team.getTeamName(), teamName)) {
            team.setTeamName(teamName);
        }
        if (!Objects.equals(team.getTeamCoach(), teamCoach)) {
            team.setTeamCoach(teamCoach);
        }
    }

    public void deleteTeam(Long teamID) {
        boolean exists = teamRepository.existsById(teamID);
        if (!exists) {
            throw new NotFoundException("team with id " + teamID + " does not exist");
        }
        teamRepository.deleteById(teamID);
    }

    public void addTeamPlayer(Long teamID, Long playerID) {
        Optional<Team> teamOptional = teamRepository.findById(teamID);
        Optional<Player> playerOptional = playerRepository.findById(playerID);
        if (checkIsPresent(teamOptional, playerOptional)) {
            Team team = teamOptional.get();
            Player player = playerOptional.get();
            team.addTeamPlayer(player);
            teamRepository.save(team);
        } else {
            throw new NotFoundException("team with id " + teamID + "or player with id " + playerID + "does not exist");
        }
    }

    public void removeTeamPlayer(Long teamID, Long playerID) {
        Optional<Team> teamOptional = teamRepository.findById(teamID);
        Optional<Player> playerOptional = playerRepository.findById(playerID);
        if (checkIsPresent(teamOptional, playerOptional)) {
            Team team = teamOptional.get();
            Player player = playerOptional.get();
            team.removeTeamPlayer(player);
            teamRepository.save(team);
        } else {
            throw new NotFoundException("team with id " + teamID + "or player with id " + playerID + "does not exist");
        }
    }
}
