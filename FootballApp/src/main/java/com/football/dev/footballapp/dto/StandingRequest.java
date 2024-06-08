package com.football.dev.footballapp.dto;

public record StandingRequest(Long id,Long club_id,Integer wonMatches,Integer drawMatches,Integer lostMatches,Integer goalScored,Integer goalConceded,Integer points,Long season_id) {
}
