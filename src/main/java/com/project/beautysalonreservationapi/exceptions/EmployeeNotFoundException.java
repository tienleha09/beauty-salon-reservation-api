package com.project.beautysalonreservationapi.exceptions;

import java.util.NoSuchElementException;

public class EmployeeNotFoundException extends NoSuchElementException {

    public EmployeeNotFoundException(long id) {
        super("Could not find employee with id " + id);

    }
}
