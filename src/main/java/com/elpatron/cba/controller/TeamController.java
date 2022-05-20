package com.elpatron.cba.controller;

import com.elpatron.cba.dto.TeamDTO;
import com.elpatron.cba.model.Player;
import com.elpatron.cba.model.Team;
import com.elpatron.cba.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cba/teams")
public class TeamController {
    private TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public List<TeamDTO> getAllTeams() {
        return teamService.getAllTeams();
    }

    @GetMapping("{teamID}")
    public Team showTeamDetails(
            @PathVariable("teamID") Long teamID
    ) {
        return teamService.getTeamDetails(teamID);
    }

    @PostMapping("add")
    public void registerNewTeam(
            @RequestBody Team team
    ) {
        teamService.addNewTeam(team);
    }

    @PutMapping("update/{teamID}")
    public void updateTeam(
            @PathVariable("teamID") Long teamID,
            @RequestBody Team team
    ) {
        teamService.updateTeam(team, teamID);
    }

    @DeleteMapping("delete/{teamID}")
    public void deleteTeam(
            @PathVariable("teamID") Long teamID
    ) {
        teamService.deleteTeam(teamID);
    }

    @PostMapping("{teamID}/add/{playerID}")
    public void addTeamPlayer(
            @PathVariable("teamID") Long teamID,
            @PathVariable("playerID") Long playerID
    ) {
        teamService.addTeamPlayer(teamID, playerID);
    }

    @DeleteMapping("{teamID}/remove/{playerID}")
    public void removeTeamPlayer(
            @PathVariable("teamID") Long teamID,
            @PathVariable("playerID") Long playerID
    ) {
        teamService.removeTeamPlayer(teamID, playerID);
    }
}
