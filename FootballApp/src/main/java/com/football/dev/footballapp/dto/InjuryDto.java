package com.football.dev.footballapp.dto;
import com.football.dev.footballapp.models.enums.InjuryStatus;
import lombok.*;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InjuryDto {
    private String injuryType;
    private String injuryDate;
    private Date expectedRecoveryTime;
    private InjuryStatus injuryStatus;
}
