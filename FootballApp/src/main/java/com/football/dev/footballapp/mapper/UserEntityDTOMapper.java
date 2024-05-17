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
        return new UserEntity(userEntityDto.firstName(),
                userEntityDto.lastName(),userEntityDto.email(),
                userEntityDto.phone(), userEntityDto.country(),
                userEntityDto.birthDate(),userEntityDto.address(),
                userEntityDto.city(),userEntityDto.postal_code(), userEntityDto.gender(),
                userEntityDto.enabled());
    }
}