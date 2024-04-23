package com.football.dev.footballapp.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String formatted) {
        super(formatted);
    }
}
