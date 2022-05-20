package com.elpatron.cba.service;

import com.elpatron.cba.dto.TeamDTO;
import com.elpatron.cba.exception.BadRequestException;
import com.elpatron.cba.exception.NotFoundException;
import com.elpatron.cba.model.Player;
import com.elpatron.cba.model.Team;
import com.elpatron.cba.repository.PlayerRepository;
import com.elpatron.cba.repository.TeamRepository;
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
    public static final String TEAM_WITH_ID_D_NOT_FOUND = "team with id %d not found";
    public static final String TEAM_WITH_ID_D_OR_PLAYER_WITH_ID_S_NOT_FOUND = "team with id %d or player with id %s not found";
    public static final String TEAM_ALREADY_EXISTS = "team already exists";
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
        Optional<Team> teamOptional = teamRepository.findById(teamID);
        if (!teamOptional.isPresent()) {
            throw new NotFoundException(String.format(TEAM_WITH_ID_D_NOT_FOUND, teamID));
        }
        return teamOptional.get();
    }

    private TeamDTO teamDTO(Team team) {
        return new TeamDTO(team.getTeamID(), team.getTeamCity(), team.getTeamName());
    }

    public void addNewTeam(Team team) {
        if (teamRepository.existsTeamByTeamName(team.getTeamName())) {
            throw new BadRequestException(TEAM_ALREADY_EXISTS);
        }
        teamRepository.save(team);
    }

    @Transactional
    public void updateTeam(Team team, Long teamID) {
        Team existingTeam = teamRepository.findById(teamID)
                .orElseThrow(() -> new NotFoundException(
                        String.format(TEAM_WITH_ID_D_NOT_FOUND, teamID)
                ));
        existingTeam.setTeamCity(team.getTeamCity());
        if (!Objects.equals(existingTeam.getTeamName(), team.getTeamName())) {
            if (teamRepository.existsTeamByTeamName(team.getTeamName())) {
                throw new BadRequestException(TEAM_ALREADY_EXISTS);
            } else {
                existingTeam.setTeamName(team.getTeamName());
            }
        }
        existingTeam.setTeamCoach(team.getTeamCoach());
        teamRepository.save(existingTeam);
    }

    public void deleteTeam(Long teamID) {
        boolean exists = teamRepository.existsById(teamID);
        if (!exists) {
            throw new NotFoundException(String.format(TEAM_WITH_ID_D_NOT_FOUND, teamID));
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
            player.setValid(false);
            teamRepository.save(team);
        } else {
            throw new NotFoundException(String.format(TEAM_WITH_ID_D_OR_PLAYER_WITH_ID_S_NOT_FOUND, teamID, playerID));
        }
    }

    public void removeTeamPlayer(Long teamID, Long playerID) {
        Optional<Team> teamOptional = teamRepository.findById(teamID);
        Optional<Player> playerOptional = playerRepository.findById(playerID);
        if (checkIsPresent(teamOptional, playerOptional)) {
            Team team = teamOptional.get();
            Player player = playerOptional.get();
            team.removeTeamPlayer(player);
            player.setValid(true);
            teamRepository.save(team);
        } else {
            throw new NotFoundException(String.format(TEAM_WITH_ID_D_OR_PLAYER_WITH_ID_S_NOT_FOUND, teamID, playerID));
        }
    }
}
