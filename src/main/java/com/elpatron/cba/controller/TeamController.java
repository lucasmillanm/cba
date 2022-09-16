package com.elpatron.cba.controller;

import com.elpatron.cba.dto.TeamDTO;
import com.elpatron.cba.model.Team;
import com.elpatron.cba.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cba/teams")
public class TeamController {
    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public ResponseEntity<List<TeamDTO>> getAllTeams() {
        return ResponseEntity.ok().body(teamService.getAllTeams());
    }

    @GetMapping("/{teamID}")
    public ResponseEntity<Team> showTeamDetails(
            @PathVariable("teamID") Long teamID
    ) {
        return ResponseEntity.ok().body(teamService.getTeamDetails(teamID));
    }

    @PostMapping
    public ResponseEntity<Team> addNewTeam(
            @Valid
            @RequestBody Team team
    ) {
        URI uri = URI.create(String.valueOf(ServletUriComponentsBuilder.fromCurrentContextPath().path("/cba/teams/add")));
        return ResponseEntity.created(uri).body(teamService.addNewTeam(team));
    }

    @PutMapping("/{teamID}")
    public void updateTeam(
            @Valid
            @PathVariable("teamID") Long teamID,
            @RequestBody Team team
    ) {
        teamService.updateTeam(teamID, team);
    }

    @DeleteMapping("/{teamID}")
    public void deleteTeam(
            @PathVariable("teamID") Long teamID
    ) {
        teamService.deleteTeam(teamID);
    }

    @PostMapping("/{teamID}/add-player")
    public void addTeamPlayers(
            @Valid
            @PathVariable("teamID") Long teamID,
            @RequestBody List<Long> playerIDs
    ) {
        teamService.addTeamPlayers(teamID, playerIDs);
    }

    @DeleteMapping("/{teamID}/remove/{playerID}")
    public void removeTeamPlayer(
            @PathVariable("teamID") Long teamID,
            @PathVariable("playerID") Long playerID
    ) {
        teamService.removeTeamPlayer(teamID, playerID);
    }
}
