package com.elpatron.cba.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min = 2, max = 30)
    @NotBlank(message = "username cannot be empty")
    private String username;

    @Size(min = 2, max = 30)
    @NotBlank(message = "name cannot be empty")
    private String name;

    @Size(min = 3, max = 64)
    @NotBlank(message = "password cannot be empty")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();
}
