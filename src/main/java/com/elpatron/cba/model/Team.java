package com.elpatron.cba.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long teamID;

    @NotBlank(message = "teamCity cannot be empty")
    private String teamCity;

    @NotBlank(message = "teamName cannot be empty")
    private String teamName;

    @NotBlank(message = "teamCoach cannot be empty")
    private String teamCoach;

    @OneToMany
    private List<Player> teamPlayers = new ArrayList<>();

    public Team(String teamCity, String teamName, String teamCoach, List<Player> teamPlayers) {
        this.teamCity = teamCity;
        this.teamName = teamName;
        this.teamCoach = teamCoach;
        this.teamPlayers = teamPlayers;
    }

    public void addTeamPlayers(List<Player> players) {
        teamPlayers.addAll(players);
    }

    public void removeTeamPlayer(Player player) {
        teamPlayers.remove(player);
    }
}