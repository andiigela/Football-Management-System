package com.football.dev.footballapp.mapper;

import com.football.dev.footballapp.dto.UserEntityDto;
import com.football.dev.footballapp.models.UserEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.function.Function;
@Service
public class UserEntityDTOMapper implements Function<UserEntity, UserEntityDto> {
    @Override
    public UserEntityDto apply(UserEntity userEntity) {
        return new UserEntityDto(userEntity.getId(),userEntity.getFirstName(),userEntity.getLastName(),userEntity.getEmail(), userEntity.getPhone(), userEntity.getCountry(),userEntity.getBirthDate(),userEntity.getRole().getDescription(),userEntity.getProfile_picture(),userEntity.getAddress(),userEntity.getCity(),userEntity.getPostal_code());
    }
}
