package com.elpatron.cba.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeamDTO {
    private Long teamID;
    private String teamCity;
    private String teamName;
    private String teamCoach;
}
