package com.football.dev.footballapp.dto;

import com.football.dev.footballapp.models.Club;
import com.football.dev.footballapp.models.Player;
import jakarta.persistence.ManyToOne;

import java.util.Date;

public record TransferDTO(Long id ,
                           Player player,
                           Club previousClub,
                           Club newClub,
                           Date transferDate,
                           Double transferFee
) {
}
