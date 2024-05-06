package com.football.dev.footballapp.models;

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
    @OneToOne
    private Club homeTeamId;
    @OneToOne
    private Club awayTeamId;
    private LocalDateTime matchDate;
    @OneToOne
    private Stadium stadium;
    private String result;
    private Integer homeTeamScore;
    private Integer awayTeamScore;
    @ManyToOne
    private Season season;

}
