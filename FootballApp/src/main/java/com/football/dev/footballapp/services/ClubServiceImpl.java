package com.football.dev.footballapp.services;

import com.football.dev.footballapp.dto.ClubDto;
import com.football.dev.footballapp.models.Club;
import com.football.dev.footballapp.models.UserEntity;
import com.football.dev.footballapp.repository.ClubRepository;
import com.football.dev.footballapp.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class ClubServiceImpl implements ClubService {

    private final ClubRepository clubRepository;
    private final UserRepository userRepository;
    private final Function<ClubDto, Club> clubDtoToClub;

    public ClubServiceImpl(ClubRepository clubRepository, Function<ClubDto, Club> clubDtoToClub, UserRepository userRepository) {
        this.clubRepository = clubRepository;
        this.clubDtoToClub = clubDtoToClub;
        this.userRepository = userRepository;
    }

    @Override
    public void saveClub(ClubDto clubDto) {
        Club club = clubDtoToClub.apply(clubDto);
        if(club == null) return;
        Club clubDb = clubRepository.findClubByUserEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(clubDb == null) throw new EntityNotFoundException("User is not authenticated.");
        club.setStadium(clubDb.getStadium());
        club.setLeague(clubDb.getLeague());
        Optional<UserEntity> optionalUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        UserEntity authenticatedUser = optionalUser.orElseThrow(() -> new EntityNotFoundException("User is not authenticated."));
        club.setUser(authenticatedUser);
        clubRepository.save(club);
    }

    @Override
    public void updateClub(ClubDto clubDto, Long id) {
        if(clubDto == null) return;
        Club clubDb = clubRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Club not found with id: " + id));
        clubDb.setName(clubDto.getName());
        clubDb.setStadium(clubDto.getStadium());
        clubDb.setFoundedYear(clubDto.getFoundedYear());
        clubDb.setCity(clubDto.getCity());
        clubDb.setWebsite(clubDto.getWebsite());
        clubDb.setLeague(clubDto.getLeague());
        clubRepository.save(clubDb);
    }

    @Override
    public void deleteClub(Long id) {
        Club clubDb = this.getClubById(id);
        if(clubDb == null) throw new EntityNotFoundException("Club not found with specified id: " + id);
        clubDb.isDeleted = true;
        clubRepository.save(clubDb);
    }

    @Override
    public Club getClubById(Long id) {
        if (id == null || id <= 0) throw new IllegalArgumentException("Club id must be a positive non-zero value");
        return clubRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Club not found with id: " + id));

    }

    @Override
    public List<Club> getAllClubs() {
        return clubRepository.findAll();
    }
}
