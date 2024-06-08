package com.football.dev.footballapp.dto;

import com.football.dev.footballapp.models.enums.TypeStatus;

public record MatchEventRequest(Long id ,
                                Integer minute,
                                TypeStatus type,
                                Long club_id,
                                Long player_id,
                                Long assist_id,
                                Long subsitutePlayer_id,
                                boolean isPenalty,
                                boolean isOwnGoal,
                                boolean isRedCard,
                                boolean isYellowCard,
                                String description,
                                Long match_id) {

}
