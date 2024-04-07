package com.football.dev.footballapp.models;

import com.football.dev.footballapp.models.enums.Foot;
import com.football.dev.footballapp.models.enums.FootballPosition;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.util.List;

@Entity
@Table(name = "players")
@NoArgsConstructor
@Getter
@Setter
@Where(clause = "is_deleted=false")
public class Player extends BaseEntity {
    @OneToOne
    private UserEntity userEntity;
    @ManyToOne
    private Club club;
    @ElementCollection(targetClass = FootballPosition.class)
    @CollectionTable(name = "player_positions", joinColumns = @JoinColumn(name = "player_id"))
    @Enumerated(value = EnumType.STRING)
    private List<FootballPosition> position;
    private Integer shirtNumber;
    private Double height;
    private Double weight;
    public String name;
    @Enumerated(value = EnumType.STRING)
    private Foot preferred_foot;

}
