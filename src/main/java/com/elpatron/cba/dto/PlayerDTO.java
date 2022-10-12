package com.elpatron.cba.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayerDTO {
    private Long playerID;
    private String firstName;
    private String lastName;
    private String position;
    private int number;
}
