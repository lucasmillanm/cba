package com.elpatron.cba.service;

import com.elpatron.cba.model.Player;
import com.elpatron.cba.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public void addNewPlayer(Player player) {
       /*Optional<Player> playerOptional = playerRepository.findPlayerByNumber(player.getNumber());
        if (playerOptional.isPresent()) {
            throw new IllegalStateException("number taken");
        }*/
        playerRepository.save(player);
    }

    @Transactional
    public void updatePlayer(Long playerID, String firstName, String lastName, String pos, Integer number) {
        Player player = playerRepository.findById(playerID)
                .orElseThrow(() -> new IllegalStateException(
                        "player with id " + playerID + " does not exist"
                ));
        if (!Objects.equals(player.getFirstName(), firstName)) {
            player.setFirstName(firstName);
        }
        if (!Objects.equals(player.getLastName(), lastName)) {
            player.setLastName(lastName);
        }
        if (!Objects.equals(player.getPos(), pos)) {
            player.setPos(pos);
        }
        if (number != null &&
                !Objects.equals(player.getNumber(), number)) {
            /*Optional<Player> studentOptional = playerRepository
                    .findPlayerByNumber(number);
            if (studentOptional.isPresent()) {
                throw new IllegalStateException("number taken");
            }*/
            player.setNumber(number);
        }
    }

    public void deletePlayer(Long playerID) {
        boolean exists = playerRepository.existsById(playerID);
        if (!exists) {
            throw new IllegalStateException("player with id " + playerID + " does not exist");
        }
        playerRepository.deleteById(playerID);
    }
}
