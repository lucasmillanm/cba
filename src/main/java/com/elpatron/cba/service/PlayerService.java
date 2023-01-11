package com.elpatron.cba.service;

import com.elpatron.cba.dto.PlayerDTO;
import com.elpatron.cba.exception.NotFoundException;
import com.elpatron.cba.model.Player;
import com.elpatron.cba.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlayerService {
    public static final String PLAYER_WITH_ID_D_NOT_FOUND = "player with id %d not found";
    private final PlayerRepository playerRepository;

    private PlayerDTO playerDTO(Player player) {
        return new PlayerDTO(player.getPlayerID(), player.getFirstName(), player.getLastName(), player.getPosition(), player.getNumber());
    }

    public List<PlayerDTO> getAllPlayers() {
        log.info("fetching all players");
        return playerRepository.findAll()
                .stream()
                .map(this::playerDTO)
                .collect(Collectors.toList());
    }

    public List<Player> getValidPlayers() {
        log.info("fetching all valid players");
        return playerRepository.findAll()
                .stream()
                .filter(Player::isValid)
                .collect(Collectors.toList());
    }

    public Player getPlayerDetails(Long playerID) {
        Player existingPlayer = playerRepository.findById(playerID)
                .orElseThrow(() -> new NotFoundException(
                        String.format(PLAYER_WITH_ID_D_NOT_FOUND, playerID)
                ));
        log.info("fetching player");
        return existingPlayer;
    }

    public Player addNewPlayer(Player player) {
        log.info("adding new player {} {}", player.getFirstName(), player.getLastName());
        return playerRepository.save(player);
    }

    @Transactional
    public void updatePlayer(Long playerID, Player player) {
        Player existingPlayer = playerRepository.findById(playerID)
                .orElseThrow(() -> new NotFoundException(
                        String.format(PLAYER_WITH_ID_D_NOT_FOUND, playerID)
                ));
        log.info("updating player {} {}", player.getFirstName(), player.getLastName());
        existingPlayer.setFirstName(player.getFirstName());
        existingPlayer.setLastName(player.getLastName());
        existingPlayer.setPosition(player.getPosition());
        existingPlayer.setNumber(player.getNumber());
        existingPlayer.setDescription(player.getDescription());
        playerRepository.save(existingPlayer);
    }

    public void deletePlayer(Long playerID) {
        if (!playerRepository.existsById(playerID)) {
            log.warn("player with id {} not found", playerID);
            throw new NotFoundException(String.format(PLAYER_WITH_ID_D_NOT_FOUND, playerID));
        }
        log.info("deleting player with id {}", playerID);
        playerRepository.deleteById(playerID);
    }
}
