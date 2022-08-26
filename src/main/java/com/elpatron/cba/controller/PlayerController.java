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

    @GetMapping("/validPlayers")
    public List<Player> getValidPlayers() {
        return playerService.getValidPlayers();
    }

    @GetMapping("/{playerID}")
    public Player showPlayerDetails(
            @PathVariable("playerID") Long playerID
    ) {
        return playerService.getPlayerDetails(playerID);
    }

    @PostMapping("/add")
    public void registerNewPlayer(
            @RequestBody Player player
    ) {
        playerService.addNewPlayer(player);
    }

    @PutMapping("/update/{playerID}")
    public void updatePlayer(
            @PathVariable("playerID") Long playerID,
            @RequestBody Player player
    ) {
        playerService.updatePlayer(playerID, player);
    }

    @DeleteMapping("/delete/{playerID}")
    public void deletePlayer(
            @PathVariable("playerID") Long playerID
    ) {
        playerService.deletePlayer(playerID);
    }
}