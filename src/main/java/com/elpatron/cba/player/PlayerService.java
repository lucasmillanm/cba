package com.elpatron.cba.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
        Optional<Player> playerOptional = playerRepository.findPlayerByNumber(player.getNumber());
        if (playerOptional.isPresent()) {
            throw new IllegalStateException("number taken");
        }
        playerRepository.save(player);
    }

    private boolean notEmpty(String name) {
        return name != null && name.length() > 0;
    }

    @Transactional
    public void updatePlayer(Long playerID, String firstName, String lastName, Integer number, String pos) {
        Player player = playerRepository.findById(playerID)
                .orElseThrow(() -> new IllegalStateException(
                        "player with id " + playerID + " does not exist"
                ));
        if (notEmpty(firstName) &&
                !Objects.equals(player.getFirstName(), firstName)) {
            player.setFirstName(firstName);
        }
        if (notEmpty(lastName) &&
                !Objects.equals(player.getLastName(), lastName)) {
            player.setLastName(lastName);
        }

        if (number != null &&
                !Objects.equals(player.getNumber(), number)) {
            Optional<Player> studentOptional = playerRepository
                    .findPlayerByNumber(number);
            if (studentOptional.isPresent()) {
                throw new IllegalStateException("number taken");
            }
            player.setNumber(number);
        }
        if (notEmpty(pos) &&
                !Objects.equals(player.getPos(), pos)) {
            player.setPos(pos);
        }
    }

    public void deletePlayer(Long playerID) {
        boolean exists = playerRepository.existsById(playerID);
        if (!exists) {
            throw new IllegalStateException("student with id " + playerID + " does not exist");
        }
        playerRepository.deleteById(playerID);
    }
}
