package com.elpatron.cba.player;

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

    @PostMapping
    public void registerNewPlayer(@RequestBody Player player) {
        playerService.addNewPlayer(player);
    }

    @PutMapping(path = "{playerID}")
    public void updatePlayer(
            @PathVariable("playerID") Long playerID,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) Integer number,
            @RequestParam(required = false) String pos) {
        playerService.updatePlayer(playerID, firstName, lastName, number, pos);
    }

    @DeleteMapping(path = "{playerID}")
    public void deletePlayer(@PathVariable("playerID") Long playerID){
        playerService.deletePlayer(playerID);
    }
}
