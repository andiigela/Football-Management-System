package com.football.dev.footballapp.mapper;

import com.football.dev.footballapp.dto.UserEntityDto;
import com.football.dev.footballapp.models.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public class UserEntityDTOMapper implements Function<UserEntityDto, UserEntity> {
    @Override
    public UserEntity apply(UserEntityDto userEntityDto) {
        return new UserEntity(userEntityDto.getFirstName(),
                userEntityDto.getLastName(),userEntityDto.getEmail(),
                userEntityDto.getPhone(), userEntityDto.getCountry(),
                userEntityDto.getBirthDate(),userEntityDto.getAddress(),
                userEntityDto.getCity(),userEntityDto.getPostal_code(), userEntityDto.getGender());
    }
}