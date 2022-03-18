package com.elpatron.cba.service;

import com.elpatron.cba.exception.NotFoundException;
import com.elpatron.cba.model.Player;
import com.elpatron.cba.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PlayerService {
    public static final String PLAYER_WITH_ID_D_NOT_FOUND = "player with id %d not found";
    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public Player getPlayerDetails(Long playerID) {
        Optional<Player> playerOptional = playerRepository.findById(playerID);
        if (!playerOptional.isPresent()) {
            throw new NotFoundException(String.format(PLAYER_WITH_ID_D_NOT_FOUND, playerID));
        }
        return playerOptional.get();
    }

    public void addNewPlayer(Player player) {
        playerRepository.save(player);
    }

    @Transactional
    public void updatePlayer(Long playerID, String firstName, String lastName, String pos, Integer number) {
        Player player = playerRepository.findById(playerID)
                .orElseThrow(() -> new NotFoundException(
                        String.format(PLAYER_WITH_ID_D_NOT_FOUND, playerID)
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
            player.setNumber(number);
        }
    }

    public void deletePlayer(Long playerID) {
        boolean exists = playerRepository.existsById(playerID);
        if (!exists) {
            throw new NotFoundException(String.format(PLAYER_WITH_ID_D_NOT_FOUND, playerID));
        }
        playerRepository.deleteById(playerID);
    }
}
