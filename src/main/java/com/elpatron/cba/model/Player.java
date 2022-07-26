package com.elpatron.cba.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;

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

    @Size(min = 2, max = 30)
    @NotBlank(message = "firstName cannot be empty")
    @Column(name = "first_name")
    private String firstName;

    @Size(min = 2, max = 30)
    @NotBlank(message = "lastName cannot be empty")
    @Column(name = "last_name")
    private String lastName;

    @NotBlank(message = "position cannot be empty")
    @Column(name = "position")
    private String position;

    @Min(0)
    @Max(99)
    @NotNull(message = "number cannot be empty")
    @Column(name = "number")
    private int number;

    @Size(max = 255)
    @Column(name = "description")
    private String description;

    @Column(name = "is_valid")
    private boolean isValid = true;

    public Player(String firstName, String lastName, String position, int number, String description, boolean isValid) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.number = number;
        this.description = description;
        this.isValid = isValid;
    }
}
