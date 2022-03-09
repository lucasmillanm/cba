package com.elpatron.cba.player;

import com.elpatron.cba.team.Team;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "player")
public class Player {
    @Id
    @SequenceGenerator(
            name = "player_sequence",
            sequenceName = "player_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "player_sequence"
    )
    public Long playerID;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "number")
    private Integer number;

    @Column(name = "pos")
    private String pos;

    public Player(String firstName, String lastName, Integer number, String pos) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.number = number;
        this.pos = pos;
    }

}
