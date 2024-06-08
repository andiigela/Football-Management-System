package com.football.dev.footballapp.dto;

import com.football.dev.footballapp.models.enums.TypeStatus;

public record MatchEventDTO(
  Long id,
  Integer minute,
  TypeStatus type,
  ClubDto club_id,
  PlayerDto player,
  PlayerDto assisted,
  PlayerDto substitutePlayer,
  boolean isPenalty,
  boolean isOwnGoal,
  boolean redCard,
  boolean yellowCard,
  String description,
  MatchDTO matchId
) {}
