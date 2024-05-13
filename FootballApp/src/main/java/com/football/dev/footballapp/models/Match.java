package com.football.dev.footballapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Table(name = "matches")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Where(clause = "is_deleted=false")
public class Match extends BaseEntity {
    @ManyToOne
    private Club homeTeamId;
    @ManyToOne
    private Club awayTeamId;
    private LocalDateTime matchDate;
    @OneToOne
    private Stadium stadium;
    private String result;
    private Integer homeTeamScore;
    private Integer awayTeamScore;
    @ManyToOne
    private Round round;


    public Match(Club homeTeamId, Club awayTeamId, LocalDateTime matchDate, Stadium stadium, String result, Integer homeTeamScore, Integer awayTeamScore) {
        this.homeTeamId = homeTeamId;
        this.awayTeamId = awayTeamId;
        this.matchDate = matchDate;
        this.stadium = stadium;
        this.result = result;
        this.homeTeamScore = homeTeamScore;
        this.awayTeamScore = awayTeamScore;
    }
}
