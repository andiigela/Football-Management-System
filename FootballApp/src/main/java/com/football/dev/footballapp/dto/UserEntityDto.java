package com.football.dev.footballapp.dto;


import java.util.Date;


public record UserEntityDto (Long dbId, String firstName, String lastName, String email, String phone, String country, Date birthDate,
                             String profile_picture, String address, String city, String postal_code, String gender, boolean enabled){
}
