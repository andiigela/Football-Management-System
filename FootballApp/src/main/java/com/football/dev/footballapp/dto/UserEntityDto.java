package com.football.dev.footballapp.dto;


import com.football.dev.footballapp.models.enums.Gender;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

public record UserEntityDto (String firstName, String lastName, String email, String phone, String country, Date birthDate,
                            String profile_picture, String address, String city, String postal_code, Gender gender){
}
