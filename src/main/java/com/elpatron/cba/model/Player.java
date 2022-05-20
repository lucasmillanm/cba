package com.elpatron.cba.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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

    @NotBlank(message = "firstName cannot be empty")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "lastName cannot be empty")
    @Column(name = "last_name")
    private String lastName;

    @NotBlank(message = "position cannot be empty")
    @Column(name = "pos")
    private String pos;

    @Min(0)
    @Max(99)
    @NotNull(message = "number cannot be empty")
    @Column(name = "number")
    private int number;

    @Column(name = "is_valid")
    private boolean isValid = true;

    public Player(String firstName, String lastName, String pos, int number, boolean isValid) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.pos = pos;
        this.number = number;
        this.isValid = isValid;
    }
}
