package com.football.dev.footballapp.dto;

import com.football.dev.footballapp.models.Match;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeasonDto {
    private Long id;
    private String name;
    private List<Match> matches;
    private boolean isDeleted;
}
