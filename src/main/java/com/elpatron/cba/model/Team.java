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

    @NotBlank(message = "teamCity cannot be empty")
    @Column
    private String teamCity;

    @NotBlank(message = "teamName cannot be empty")
    @Column
    private String teamName;

    @NotBlank(message = "teamCoach cannot be empty")
    @Column
    private String teamCoach;

    @OneToMany
    private List<Player> teamPlayers = new ArrayList<>();

    public Team(String teamCity, String teamName, String teamCoach, List<Player> teamPlayers) {
        this.teamCity = teamCity;
        this.teamName = teamName;
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