package com.football.dev.footballapp.services.impl;

import com.football.dev.footballapp.dto.ClubDto;
import com.football.dev.footballapp.exceptions.ClubNotFoundException;
import com.football.dev.footballapp.exceptions.UserNotFoundException;
import com.football.dev.footballapp.models.Club;
import com.football.dev.footballapp.models.UserEntity;
import com.football.dev.footballapp.repository.esrepository.ClubRepositoryES;
import com.football.dev.footballapp.repository.jparepository.ClubRepository;
import com.football.dev.footballapp.repository.jparepository.UserRepository;
import com.football.dev.footballapp.services.ClubService;
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
    private final ClubRepositoryES clubRepositoryES;

    public ClubServiceImpl(ClubRepository clubRepository,
                           Function<ClubDto, Club> clubDtoToClub,
                           UserRepository userRepository,
                           ClubRepositoryES clubRepositoryES) {
        this.clubRepository = clubRepository;
        this.clubDtoToClub = clubDtoToClub;
        this.userRepository = userRepository;
        this.clubRepositoryES = clubRepositoryES;
    }

    @Override
    public void saveClub(ClubDto clubDto) {
        Club club = clubDtoToClub.apply(clubDto);
        if(club == null) return;
        Club clubDb = clubRepository.findClubByUserEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(clubDb == null) throw new EntityNotFoundException("User is not authenticated.");
        //club.setStadium(clubDb.getStadium());
        //club.setLeague(clubDb.getLeague());
        Optional<UserEntity> optionalUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        UserEntity authenticatedUser = optionalUser.orElseThrow(() -> new EntityNotFoundException("User is not authenticated."));
        club.setUser(authenticatedUser);
        clubRepository.save(club);
        clubRepositoryES.save(club);
    }

    @Override
    public void updateClub(ClubDto clubDto, Long id) {
        if(clubDto == null) return;
        Club clubDb = clubRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Club not found with id: " + id));
        clubDb.setName(clubDto.getName());
        clubDb.setFoundedYear(clubDto.getFoundedYear());
        clubDb.setCity(clubDto.getCity());
        clubDb.setWebsite(clubDto.getWebsite());
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
    @Override
    public Long getClubIdByUserId(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Club club = user.getClub();
        if (club == null) {
            throw new ClubNotFoundException("Club not found for the user with ID: " + userId);
        }

        return club.getId();
    }

    @Override
    public Club getClubByUserId(Long userId) {
        Optional<UserEntity> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            return user.getClub();
        } else {
            throw new IllegalArgumentException("User not found with id: " + userId);
        }
    }
}
