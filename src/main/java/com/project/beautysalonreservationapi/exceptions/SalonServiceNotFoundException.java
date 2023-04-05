package com.project.beautysalonreservationapi.exceptions;

public class SalonServiceNotFoundException extends RuntimeException {

    public SalonServiceNotFoundException(long id) {
        super("Could not find service with id " + id);

    }
}
