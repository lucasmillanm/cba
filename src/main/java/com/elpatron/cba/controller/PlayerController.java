package com.elpatron.cba.controller;

import com.elpatron.cba.model.Player;
import com.elpatron.cba.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
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
    public ResponseEntity<List<Player>> getAllPlayers() {
        return ResponseEntity.ok().body(playerService.getAllPlayers());
    }

    @GetMapping("/valid-players")
    public ResponseEntity<List<Player>> getValidPlayers() {
        return ResponseEntity.ok().body(playerService.getValidPlayers());
    }

    @GetMapping("/{playerID}")
    public ResponseEntity<Player> showPlayerDetails(
            @PathVariable("playerID") Long playerID
    ) {
        return ResponseEntity.ok().body(playerService.getPlayerDetails(playerID));
    }

    @PostMapping
    public ResponseEntity<Player> addNewPlayer(
            @Valid
            @RequestBody Player player
    ) {
        URI uri = URI.create(String.valueOf(ServletUriComponentsBuilder.fromCurrentContextPath().path("/cba/players")));
        return ResponseEntity.created(uri).body(playerService.addNewPlayer(player));
    }

    @PutMapping("/{playerID}")
    public void updatePlayer(
            @Valid
            @PathVariable("playerID") Long playerID,
            @RequestBody Player player
    ) {
        playerService.updatePlayer(playerID, player);
    }

    @DeleteMapping("/{playerID}")
    public void deletePlayer(
            @PathVariable("playerID") Long playerID
    ) {
        playerService.deletePlayer(playerID);
    }
}