package com.elpatron.cba.team;

import com.elpatron.cba.player.Player;
import com.elpatron.cba.player.PlayerService;
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
    public List<Team> getAllTeams() {
        return teamService.getAllTeams();
    }

    @PostMapping
    public void registerNewTeam(@RequestBody Team team) {
        teamService.addNewTeam(team);
    }

    @PutMapping(path = "{teamID}")
    public void updateTeam(
            @PathVariable("teamID") Long teamID,
            @RequestParam(required = false) String teamName,
            @RequestParam(required = false) String teamCity,
            @RequestParam(required = false) String teamCoach) {
        teamService.updateTeam(teamID, teamName, teamCity, teamCoach);
    }

    @DeleteMapping(path = "{teamID}")
    public void deleteTeam(@PathVariable("teamID") Long teamID) {
        teamService.deleteTeam(teamID);
    }
}
