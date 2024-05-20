package com.football.dev.footballapp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Where(clause = "is_deleted=false")
public class Club extends BaseEntity {

    private String name;

    @OneToOne
    private Stadium stadium;

    private Integer foundedYear;
    private String city;
    private String website;

    @ManyToOne
    private League league;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @OneToMany(mappedBy = "homeTeamId")
    List<Match> homeMatches = new ArrayList();

    @OneToMany(mappedBy = "awayTeamId")
    List<Match> awayMatches = new ArrayList();

    @OneToMany
    private List<Player> players;

    // Add other necessary attributes

    public Club(Long id, String name, Integer foundedYear, String city, String website) {
        this.id = id;
        this.name = name;
        this.foundedYear = foundedYear;
        this.city = city;
        this.website = website;
    }
    @OneToMany(mappedBy = "club")
    private List<Contract> contracts = new ArrayList<>();
}
