package com.football.dev.footballapp.services;

import com.football.dev.footballapp.dto.ClubDto;
import com.football.dev.footballapp.models.Club;

import java.util.List;

public interface ClubService {
    void saveClub(ClubDto clubDto);
    void updateClub(ClubDto clubDto, Long id);
    void deleteClub(Long id);
    Club getClubById(Long id);
    List<ClubDto> getAllClubs();
    Long getClubIdByUserId(Long userId);
    Club getClubByUserId(Long userId);

}
