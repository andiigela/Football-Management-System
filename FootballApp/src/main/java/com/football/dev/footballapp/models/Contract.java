package com.football.dev.footballapp.models;

import com.football.dev.footballapp.models.enums.ContractType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "contracts")
@NoArgsConstructor
@Getter
@Setter
@Where(clause = "is_deleted=false")
public class Contract extends BaseEntity{
    public Contract(LocalDate startDate, LocalDate endDate,Double salary, ContractType contractType,Player player){
        this.startDate=startDate;
        this.endDate=endDate;
        this.salary=salary;
        this.contractType=contractType;
        this.player=player;
    }
    @ManyToOne
    private Player player;
    @ManyToOne
    private Club club;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double salary;
    @Enumerated(value = EnumType.STRING)
    private ContractType contractType;
}
