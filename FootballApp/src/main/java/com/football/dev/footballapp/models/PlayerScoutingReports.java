package com.football.dev.footballapp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "player_scouting_report")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PlayerScoutingReports {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Player player;

    private String strengths;

    private String weaknesses;

    private String potential;

    public PlayerScoutingReports(Player player, String strengths, String weaknesses, String potential) {
        this.player = player;
        this.strengths = strengths;
        this.weaknesses = weaknesses;
        this.potential = potential;
    }
}
