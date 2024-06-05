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
//@Where(clause = "is_deleted=false")
public class Player extends BaseEntity {
    @ManyToOne
    private Club club;
    @ElementCollection(targetClass = FootballPosition.class)
    @CollectionTable(name = "player_positions", joinColumns = @JoinColumn(name = "player_id"))
    @Enumerated(value = EnumType.STRING)
    private List<FootballPosition> position = new ArrayList<>();
    private Integer shirtNumber;
    private Double height;
    private Double weight;
    private String name;
    private String imagePath;
    @Enumerated(value = EnumType.STRING)
    private Foot preferred_foot;
    @OneToMany(mappedBy = "player",fetch = FetchType.LAZY)
    private List<Contract> contracts = new ArrayList<>();
    private Long matchesPlayed;
    private Long goals;
    private Long assist;
    private Long yellowCards;
    private Long redCards;
    public Player(String name, Double height, Double weight, Integer shirtNumber,String preferredFoot,String position){
        this.name = name;
        this.height=height;
        this.weight=weight;
        this.shirtNumber=shirtNumber;
        this.preferred_foot=getFootFromString(preferredFoot);
        this.position.add(getPositionFromString(position));
    }
    public static Foot getFootFromString(String footString) {
        try {
            return Foot.valueOf(footString.toUpperCase());
        } catch (IllegalArgumentException e) {
            // Handle invalid string gracefully (e.g., return default value or log an error)
            return null; // Or throw an exception, depending on your error handling strategy
        }
    }
    public static FootballPosition getPositionFromString(String position) {
        try {
            return FootballPosition.valueOf(position.toUpperCase());
        } catch (IllegalArgumentException e) {
            // Handle invalid string gracefully (e.g., return default value or log an error)
            return null; // Or throw an exception, depending on your error handling strategy
        }
    }
}
