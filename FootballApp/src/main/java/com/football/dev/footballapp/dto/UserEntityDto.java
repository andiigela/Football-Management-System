package com.football.dev.footballapp.dto;


import java.util.Date;
import java.util.List;

public record UserEntityDto (Long id , String firstName, String lastName, String email, String phone, String country, Date birthDate,
                             String role,  String profilePicture, String address, String city, String postalCode){
}
