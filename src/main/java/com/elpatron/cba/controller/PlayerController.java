package com.elpatron.cba.controller;

import com.elpatron.cba.model.Player;
import com.elpatron.cba.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @PostMapping
    public void registerNewPlayer(
            @Valid
            @RequestBody Player player
    ) {
        playerService.addNewPlayer(player);
    }

    @PutMapping("{playerID}")
    public void updatePlayer(
            @Valid
            @PathVariable("playerID") Long playerID,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String pos,
            @RequestParam(required = false) Integer number
    ) {
        playerService.updatePlayer(playerID, firstName, lastName, pos, number);
    }

    @DeleteMapping("{playerID}")
    public void deletePlayer(
            @Valid
            @PathVariable("playerID") Long playerID
    ) {
        playerService.deletePlayer(playerID);
    }
}