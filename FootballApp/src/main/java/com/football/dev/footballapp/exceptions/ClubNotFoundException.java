package com.football.dev.footballapp.exceptions;

public class ClubNotFoundException extends RuntimeException {

    public ClubNotFoundException(String message) {
        super(message);
    }

    public ClubNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
