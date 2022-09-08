package com.elpatron.cba.service;

import com.elpatron.cba.exception.NotFoundException;
import com.elpatron.cba.model.Player;
import com.elpatron.cba.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlayerService {
    public static final String PLAYER_WITH_ID_D_NOT_FOUND = "player with id %d not found";
    private final PlayerRepository playerRepository;

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public List<Player> getValidPlayers() {
        return playerRepository.findAll()
                .stream()
                .filter(Player::isValid)
                .collect(Collectors.toList());
    }

    public Player getPlayerDetails(Long playerID) {
        Optional<Player> playerOptional = playerRepository.findById(playerID);
        if (playerOptional.isEmpty()) {
            throw new NotFoundException(String.format(PLAYER_WITH_ID_D_NOT_FOUND, playerID));
        }
        return playerOptional.get();
    }

    public void addNewPlayer(Player player) {
        playerRepository.save(player);
    }

    @Transactional
    public void updatePlayer(Long playerID, Player player) {
        Player existingPlayer = playerRepository.findById(playerID)
                .orElseThrow(() -> new NotFoundException(
                        String.format(PLAYER_WITH_ID_D_NOT_FOUND, playerID)
                ));
        existingPlayer.setFirstName(player.getFirstName());
        existingPlayer.setLastName(player.getLastName());
        existingPlayer.setPosition(player.getPosition());
        existingPlayer.setNumber(player.getNumber());
        existingPlayer.setDescription(player.getDescription());
    }

    public void deletePlayer(Long playerID) {
        if (!playerRepository.existsById(playerID)) {
            throw new NotFoundException(String.format(PLAYER_WITH_ID_D_NOT_FOUND, playerID));
        }
        playerRepository.deleteById(playerID);
    }
}
