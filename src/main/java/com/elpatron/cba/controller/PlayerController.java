package com.elpatron.cba.controller;

import com.elpatron.cba.model.Player;
import com.elpatron.cba.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cba/players")
public class PlayerController {
    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public List<Player> getAllPlayers() {
        return playerService.getAllPlayers();
    }

    @GetMapping("{playerID}")
    public Player showTeamDetails(
            @PathVariable("playerID") Long playerID
    ) {
        return playerService.getPlayerDetails(playerID);
    }

    @PostMapping
    public void registerNewPlayer(
            @RequestBody Player player
    ) {
        playerService.addNewPlayer(player);
    }

    @PutMapping("edit/{playerID}")
    public void updatePlayer(
            @PathVariable("playerID") Long playerID,
            @RequestBody Player player
    ) {
        playerService.updatePlayer(player, playerID);
    }

    @DeleteMapping("delete/{playerID}")
    public void deletePlayer(
            @PathVariable("playerID") Long playerID
    ) {
        playerService.deletePlayer(playerID);
    }
}