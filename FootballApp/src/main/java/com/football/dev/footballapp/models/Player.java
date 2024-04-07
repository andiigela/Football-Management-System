package com.football.dev.footballapp.models;

import com.football.dev.footballapp.models.enums.Foot;
import com.football.dev.footballapp.models.enums.FootballPosition;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "players")
@NoArgsConstructor
@Getter
@Setter
@Where(clause = "is_deleted=false")
public class Player extends BaseEntity {
    public Player(String name, Double height, Double weight, Integer shirtNumber){
        this.name = name;
        this.height=height;
        this.weight=weight;
        this.shirtNumber=shirtNumber;
    }
    @ManyToOne
    private Club club;
    @ElementCollection(targetClass = FootballPosition.class)
    @CollectionTable(name = "player_positions", joinColumns = @JoinColumn(name = "player_id"))
    @Enumerated(value = EnumType.STRING)
    private List<FootballPosition> position = new ArrayList<>();
    private Integer shirtNumber;
    private Double height;
    private Double weight;
    public String name;
    @Enumerated(value = EnumType.STRING)
    private Foot preferred_foot;

}
