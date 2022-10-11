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
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long playerID;

    @Size(min = 2, max = 30)
    @NotBlank(message = "firstName cannot be empty")
    private String firstName;

    @Size(min = 2, max = 30)
    @NotBlank(message = "lastName cannot be empty")
    private String lastName;

    @NotBlank(message = "position cannot be empty")
    private String position;

    @Min(0)
    @Max(99)
    @NotNull(message = "number cannot be empty")
    private int number;

    @Size(max = 255)
    private String description;

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
