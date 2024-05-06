package com.football.dev.footballapp.dto;
import com.football.dev.footballapp.models.enums.InjuryStatus;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InjuryDto {
    private Long id;
    private String injuryType;
    private LocalDate injuryDate;
    private LocalDate expectedRecoveryTime;
    private InjuryStatus injuryStatus;
}
