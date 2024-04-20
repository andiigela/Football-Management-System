package com.football.dev.footballapp.dto;

import com.football.dev.footballapp.models.League;
import com.football.dev.footballapp.models.Stadium;
import com.football.dev.footballapp.models.UserEntity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClubDto {

    private String name;
    private Stadium stadium;
    private Integer foundedYear;
    private String city;
    private String website;
    private League league;

}
