package com.football.dev.footballapp.models;

import com.football.dev.footballapp.models.enums.InjuryStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "injuries")
public class Injuries extends BaseEntity{
    @ManyToOne
    private Player player;
    private String injuryType;
    private String injuryDate;
    private Date expectedRecoveryTime;
    @Enumerated(EnumType.STRING)
    private InjuryStatus injuryStatus;
    @ManyToOne
    @JoinColumn(name = "player_scouted_id")
    private PlayerScouted playerScouted;
}
