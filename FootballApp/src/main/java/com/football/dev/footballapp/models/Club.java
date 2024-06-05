package com.football.dev.footballapp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
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
  // Add other necessary attributes

    @OneToMany(mappedBy = "club",fetch = FetchType.LAZY)
    private List<Contract> contracts ;
    @OneToMany
    private List<Match> homematches;
    @OneToMany
    private List<Match> awayMatches;
    @OneToMany(mappedBy = "club" , fetch = FetchType.LAZY )
    private List<Standing> standings;

    private Long goals;
    private Long assist;
    private Long yellowCards;
    private Long redCards;
    public Club(Long id, String name, Integer foundedYear, String city, String website) {
        this.id = id;
        this.name = name;
        this.foundedYear = foundedYear;
        this.city = city;
        this.website = website;
    }
}
