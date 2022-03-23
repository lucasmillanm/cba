package com.elpatron.cba.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestParam;

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

    @NotBlank(message = "teamCity can not be empty")
    @Column(name = "team_city")
    private String teamCity;

    @NotBlank(message = "teamName can not be empty")
    @Column(name = "team_name")
    private String teamName;

    @NotBlank(message = "teamCoach can not be empty")
    @Column(name = "team_coach")
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