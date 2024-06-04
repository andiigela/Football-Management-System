package com.football.dev.footballapp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "player_scouted")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PlayerScouted extends BaseEntity {

    @OneToOne(cascade = CascadeType.ALL)
    private Player player;

    @OneToMany(mappedBy = "playerScouted", cascade = CascadeType.ALL)
    private List<Injuries> injuries;

    @Lob
    private String report;

    public PlayerScouted(List<Injuries> injuries, String report) {
        super();
        this.injuries = injuries;
        this.report = report;
    }

    public int getTotalInjuries() {
        return injuries.size();
    }
}
