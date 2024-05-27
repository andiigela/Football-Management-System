package com.football.dev.footballapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Table(name = "matches")
@NoArgsConstructor
@Getter
@Setter
@Where(clause = "is_deleted=false")
public class Match extends BaseEntity {
    @ManyToOne(fetch = FetchType.EAGER)
    private Club homeTeamId;
    @ManyToOne(fetch = FetchType.EAGER)
    private Club awayTeamId;
    private LocalDateTime matchDate;
    @OneToOne
    private Stadium stadium;
    private String result;
    private Integer homeTeamScore;
    private Integer awayTeamScore;
    @ManyToOne
    private Round round;

    public Match(Club homeTeamId, Club awayTeamId, LocalDateTime matchDate,
                 String result, Integer homeTeamScore,
                 Integer awayTeamScore, Round round) {
        this.homeTeamId = homeTeamId;
        this.awayTeamId = awayTeamId;
        this.matchDate = matchDate;
        this.result = result;
        this.homeTeamScore = homeTeamScore;
        this.awayTeamScore = awayTeamScore;
        this.round = round;
    }
}
