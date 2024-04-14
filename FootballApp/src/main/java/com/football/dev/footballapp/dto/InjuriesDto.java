package com.football.dev.footballapp.dto;

import com.football.dev.footballapp.models.enums.InjuryStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InjuriesDto {
    private String injuryType;
    private InjuryStatus injuryStatus;

}
