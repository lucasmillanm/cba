package com.elpatron.cba.service;

import com.elpatron.cba.dto.TeamDTO;
import com.elpatron.cba.exception.MethodNotAllowedException;
import com.elpatron.cba.exception.NotFoundException;
import com.elpatron.cba.model.Player;
import com.elpatron.cba.model.Team;
import com.elpatron.cba.repository.PlayerRepository;
import com.elpatron.cba.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeamService {
    public static final String TEAM_WITH_ID_D_NOT_FOUND = "team with id %d not found";
    public static final String TEAM_WITH_ID_D_OR_PLAYER_WITH_ID_S_NOT_FOUND = "team with id %d or player with id %s not found";
    public static final String TEAM_ALREADY_EXISTS = "team already exists";
    public static final String TEAM_CONTAINS_PLAYERS = "team contains players";
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    private TeamDTO teamDTO(Team team) {
        return new TeamDTO(team.getTeamID(), team.getTeamCity(), team.getTeamName(), team.getTeamCoach());
    }

    public List<TeamDTO> getAllTeams() {
        log.info("fetching all teams");
        return teamRepository.findAll()
                .stream()
                .map(this::teamDTO)
                .collect(Collectors.toList());
    }

    public Team getTeamDetails(Long teamID) {
        Team existingTeam = teamRepository.findById(teamID)
                .orElseThrow(() -> new NotFoundException(String.format(TEAM_WITH_ID_D_NOT_FOUND, teamID)
                ));
        log.info("fetching team");
        return existingTeam;
    }


    public Team addNewTeam(Team team) {
        if (teamRepository.existsTeamByTeamName(team.getTeamName())) {
            log.warn("team already exists");
            throw new MethodNotAllowedException(TEAM_ALREADY_EXISTS);
        }
        log.info("adding new team {}", team.getTeamName());
        return teamRepository.save(team);
    }

    @Transactional
    public void updateTeam(Long teamID, Team team) {
        Team existingTeam = teamRepository.findById(teamID)
                .orElseThrow(() -> new NotFoundException(
                        String.format(TEAM_WITH_ID_D_NOT_FOUND, teamID)
                ));
        existingTeam.setTeamCity(team.getTeamCity());
        if (!Objects.equals(existingTeam.getTeamName(), team.getTeamName())) {
            if (teamRepository.existsTeamByTeamName(team.getTeamName())) {
                log.warn("team already exists");
                throw new MethodNotAllowedException(TEAM_ALREADY_EXISTS);
            } else {
                existingTeam.setTeamName(team.getTeamName());
            }
        }
        existingTeam.setTeamCoach(team.getTeamCoach());
        log.info("updating team {} {}", existingTeam.getTeamCity(), existingTeam.getTeamName());
        teamRepository.save(existingTeam);
    }

    public void deleteTeam(Long teamID) {
        if (!teamRepository.existsById(teamID)) {
            log.warn("team not found");
            throw new NotFoundException(String.format(TEAM_WITH_ID_D_NOT_FOUND, teamID));
        }
        Team team = teamRepository.getById(teamID);
        if (team.getTeamPlayers().size() >= 1) {
            log.warn("team contains players");
            throw new MethodNotAllowedException(TEAM_CONTAINS_PLAYERS);
        } else {
            log.info("deleting team with id {}", teamID);
            teamRepository.deleteById(teamID);
        }
    }

    public void addTeamPlayers(Long teamID, List<Long> playerIDs) {
        Optional<Team> teamOptional = teamRepository.findById(teamID);
        for (Long playerID : playerIDs) {
            Optional<Player> playerOptional = playerRepository.findById(playerID);
            if (teamOptional.isPresent() && playerOptional.isPresent()) {
                Team team = teamOptional.get();
                Player player = playerOptional.get();
                team.addTeamPlayer(player);
                player.setValid(false);
                log.info("adding player to team");
                teamRepository.save(team);
            } else {
                log.warn("team or player not found");
                throw new NotFoundException(String.format(TEAM_WITH_ID_D_OR_PLAYER_WITH_ID_S_NOT_FOUND, teamID, playerID));
            }
        }
    }

    public void removeTeamPlayer(Long teamID, Long playerID) {
        Optional<Team> teamOptional = teamRepository.findById(teamID);
        Optional<Player> playerOptional = playerRepository.findById(playerID);
        if (teamOptional.isPresent() && playerOptional.isPresent()) {
            Team team = teamOptional.get();
            Player player = playerOptional.get();
            List<Long> currentPlayerIDs = team.getTeamPlayers()
                    .stream()
                    .map(Player::getPlayerID)
                    .collect(Collectors.toList());
            if (!currentPlayerIDs.contains(playerID)) {
                log.warn("team does not contain the expected player");
                throw new NotFoundException("this team does not contain the expected player");
            }
            team.removeTeamPlayer(player);
            player.setValid(true);
            log.info("removing player from team");
            teamRepository.save(team);
        } else {
            log.warn("team or player not found");
            throw new NotFoundException(String.format(TEAM_WITH_ID_D_OR_PLAYER_WITH_ID_S_NOT_FOUND, teamID, playerID));
        }
    }
}
