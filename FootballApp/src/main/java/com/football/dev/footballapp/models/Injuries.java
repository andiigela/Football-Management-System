package com.football.dev.footballapp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "injuries")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Injuries extends BaseEntity {



    @ManyToOne
    @JoinColumn(name = "player_scouted_id")
    private PlayerScouted playerScouted;

    private String description;
}
