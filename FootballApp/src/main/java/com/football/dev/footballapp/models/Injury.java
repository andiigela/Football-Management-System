package com.football.dev.footballapp.models;
import com.football.dev.footballapp.models.enums.InjuryStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "injuries")
public class Injury extends BaseEntity{
    public Injury(String injuryType, LocalDate injuryDate, LocalDate expectedRecoveryTime,InjuryStatus injuryStatus,Player player){
        this.injuryType=injuryType;
        this.injuryDate=injuryDate;
        this.expectedRecoveryTime=expectedRecoveryTime;
        this.injuryStatus=injuryStatus;
        this.player=player;
    }
    @ManyToOne
    private Player player;
    private String injuryType;
    private LocalDate injuryDate;
    private LocalDate expectedRecoveryTime;
    @Enumerated(EnumType.STRING)
    private InjuryStatus injuryStatus;
}
