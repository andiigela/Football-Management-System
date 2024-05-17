package com.football.dev.footballapp.mapper;

import com.football.dev.footballapp.dto.UserEntityDto;
import com.football.dev.footballapp.models.UserEntity;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UserEntityToDTOMapper implements Function<UserEntity, UserEntityDto> {
    @Override
    public UserEntityDto apply(UserEntity userEntity) {

        return new UserEntityDto(userEntity.getFirstName(),
                userEntity.getLastName(),userEntity.getEmail(),
                userEntity.getPhone(), userEntity.getCountry(),
                userEntity.getBirthDate(),userEntity.getProfile_picture(),userEntity.getAddress(),
                userEntity.getCity(),userEntity.getPostal_code(), userEntity.getGender().toString());
    }
}
