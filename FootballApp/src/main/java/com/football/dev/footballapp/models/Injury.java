package com.football.dev.footballapp.models;
import com.football.dev.footballapp.models.enums.InjuryStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "injuries")
public class Injury extends BaseEntity{
    public Injury(String injuryType, String injuryDate, Date expectedRecoveryTime,InjuryStatus injuryStatus,Player player){
        this.injuryType=injuryType;
        this.injuryDate=injuryDate;
        this.expectedRecoveryTime=expectedRecoveryTime;
        this.injuryStatus=injuryStatus;
        this.player=player;
    }
    @ManyToOne
    private Player player;
    private String injuryType;
    private String injuryDate;
    private Date expectedRecoveryTime;
    @Enumerated(EnumType.STRING)
    private InjuryStatus injuryStatus;
}
