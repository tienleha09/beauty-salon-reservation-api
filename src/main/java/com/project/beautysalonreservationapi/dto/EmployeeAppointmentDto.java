package com.project.beautysalonreservationapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.beautysalonreservationapi.helpers.BusinessHours;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public class EmployeeAppointmentDto {
    private long id;
    @NotNull
    private String customerFullName;
    @NotNull
    private String serviceName;
    @FutureOrPresent
    @NotNull
    private LocalDate date;
    @BusinessHours
    @NotNull
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;
    @BusinessHours
    @NotNull
    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;
    private boolean isCancelled;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCustomerFullName() {
        return customerFullName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    public void setCustomerFullName(String customerFullName) {
        this.customerFullName = customerFullName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
