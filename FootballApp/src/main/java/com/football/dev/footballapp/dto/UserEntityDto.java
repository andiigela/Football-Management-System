package com.football.dev.footballapp.dto;


import com.football.dev.footballapp.models.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserEntityDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String country;
    private Date birthDate;
    private String profile_picture;
    private String address;
    private String city;
    private String postal_code;
    private Gender gender;

}



