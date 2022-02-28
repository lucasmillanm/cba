package com.elpatron.cba.team;

import javax.persistence.*;

@Entity
@Table(name = "teams")
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

    @Column(name = "teamName")
    private String teamName;

    @Column(name = "teamCity")
    private String teamCity;

    @Column(name = "teamCoach")
    private String teamCoach;

    public Team() {
    }

    public Team(String teamName, String teamCity, String teamCoach) {
        this.teamName = teamName;
        this.teamCity = teamCity;
        this.teamCoach = teamCoach;
    }

    public Long getTeamID() {
        return teamID;
    }

    public void setTeamID(Long teamID) {
        this.teamID = teamID;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamCity() {
        return teamCity;
    }

    public void setTeamCity(String teamCity) {
        this.teamCity = teamCity;
    }

    public String getTeamCoach() {
        return teamCoach;
    }

    public void setTeamCoach(String teamCoach) {
        this.teamCoach = teamCoach;
    }
}