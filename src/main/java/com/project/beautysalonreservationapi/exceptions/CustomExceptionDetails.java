package com.project.beautysalonreservationapi.exceptions;

import java.sql.Timestamp;


public class CustomExceptionDetails {
    private Timestamp timeStamp;
    private String errorMessage;
    private String description;

    public CustomExceptionDetails(Timestamp timeStamp, String errorMessage, String details) {
        this.timeStamp = timeStamp;
        this.errorMessage = errorMessage;
        this.description = details;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
