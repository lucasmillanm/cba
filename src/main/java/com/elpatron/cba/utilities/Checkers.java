package com.elpatron.cba.utilities;

import com.elpatron.cba.model.Player;
import com.elpatron.cba.model.Team;

import java.util.Optional;

public class Checkers {
    public static boolean checkIsPresent(Optional<Team> team, Optional<Player> player) {
        return team.isPresent() && player.isPresent();
    }
}
