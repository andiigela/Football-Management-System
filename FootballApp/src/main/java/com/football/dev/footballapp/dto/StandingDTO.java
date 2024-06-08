package com.football.dev.footballapp.dto;

public record StandingDTO(
  Long id,
  ClubDto club,
  Integer matchesPlayed,
  Integer wonMatches,
  Integer drawMatches,
  Integer lostMatches,
  Integer goalScored,
  Integer goalConceded,
  Integer points,
  SeasonDto seasonId
) {}
