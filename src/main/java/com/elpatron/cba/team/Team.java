package com.elpatron.cba.team;

import com.elpatron.cba.player.Player;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "team")
public class Team {
    @Id
    @SequenceGenerator(
            name = "team_sequence",
            sequenceName = "team_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "team_sequence"
    )
    private Long teamID;

    @Column(name = "team_name")
    private String teamName;

    @Column(name = "team_city")
    private String teamCity;

    @Column(name = "team_coach")
    private String teamCoach;

    @OneToMany
    private List<Player> teamPlayers = new ArrayList<>();

    public Team(String teamName, String teamCity, String teamCoach, List<Player> teamPlayers) {
        this.teamName = teamName;
        this.teamCity = teamCity;
        this.teamCoach = teamCoach;
        this.teamPlayers = teamPlayers;
    }

    public void addTeamPlayer(Player player) {
        teamPlayers.add(player);
    }

    public void removeTeamPlayer(Player player) {
        teamPlayers.remove(player);
    }

}