package com.football.dev.footballapp.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Table(name = "transfers")
public class Transfer extends BaseEntity{
    @ManyToOne
    private Player player;
    @ManyToOne
    private Club previousClub;
    @ManyToOne
    private Club newClub;
    private Date transferDate;
    private Double transferFee;

}
