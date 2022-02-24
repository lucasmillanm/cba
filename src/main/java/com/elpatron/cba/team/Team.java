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

    public Team() {
    }

    public Team(String teamName) {
        this.teamName = teamName;
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
}