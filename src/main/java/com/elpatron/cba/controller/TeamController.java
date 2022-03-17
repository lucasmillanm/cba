package com.elpatron.cba.controller;

import com.elpatron.cba.dto.TeamDTO;
import com.elpatron.cba.model.Team;
import com.elpatron.cba.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @GetMapping("{teamID}/details")
    public Team showTeamDetails(
            @PathVariable("teamID") Long teamID
    ) {
        return teamService.getTeamDetails(teamID);
    }

    @PostMapping
    public void registerNewTeam(
            @Valid
            @RequestBody Team team
    ) {
        teamService.addNewTeam(team);
    }

    @PutMapping("{teamID}")
    public void updateTeam(
            @Valid
            @PathVariable("teamID") Long teamID,
            @RequestParam(required = false) String teamCity,
            @RequestParam(required = false) String teamName,
            @RequestParam(required = false) String teamCoach
    ) {
        teamService.updateTeam(teamID, teamCity, teamName, teamCoach);
    }

    @DeleteMapping("delete/{teamID}")
    public void deleteTeam(
            @Valid
            @PathVariable("teamID") Long teamID
    ) {
        teamService.deleteTeam(teamID);
    }

    @PostMapping("{teamID}/add/{playerID}")
    public void addTeamPlayer(
            @Valid
            @PathVariable("teamID") Long teamID,
            @PathVariable("playerID") Long playerID
    ) {
        teamService.addTeamPlayer(teamID, playerID);
    }

    @DeleteMapping("{teamID}/remove/{playerID}")
    public void removeTeamPlayer(
            @Valid
            @PathVariable("teamID") Long teamID,
            @PathVariable("playerID") Long playerID
    ) {
        teamService.removeTeamPlayer(teamID, playerID);
    }
}
