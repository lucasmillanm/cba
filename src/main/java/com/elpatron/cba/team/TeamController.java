package com.elpatron.cba.team;

import com.elpatron.cba.player.Player;
import com.elpatron.cba.player.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cba/teams")
public class TeamController {
    private TeamService teamService;
    private PlayerService playerService;

    @Autowired
    public TeamController(TeamService teamService, PlayerService playerService) {
        this.teamService = teamService;
        this.playerService = playerService;
    }

    @GetMapping
    public ResponseEntity<List<Team>> getAllTeams() {
        return ResponseEntity.ok(teamService.getAllTeams());
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

    @PostMapping(path = "{teamID}/add/{playerID}")
    public void addTeamPlayer(
            @PathVariable("teamID") Long teamID,
            @PathVariable("playerID") Long playerID) {
        teamService.addTeamPlayer(teamID, playerID);
    }

   /* @DeleteMapping(path = "{teamID}/remove/{playerID}")
    public void removeTeamPlayer(
            @PathVariable("teamID") Long teamID,
            @PathVariable("playerID") Long playerID
            @RequestParam List<Player> teamPlayers) {
        teamService.removeTeamPlayer(teamID, playerID);
    }*/

}
