package com.project.beautysalonreservationapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.beautysalonreservationapi.helpers.BusinessHours;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public class CustomerReservationDto {
    private long id;
    @NotNull
    private long employeeId;

    private String employeeFullName;

    @NotNull
    private long customerId;

    private String customerFullName;
    @NotNull
    private long serviceId;

    private String serviceName;

    @FutureOrPresent
    private LocalDate date;
    @BusinessHours
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;
    @BusinessHours
    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;
    private boolean isCancelled;

    public CustomerReservationDto(){}

    public CustomerReservationDto(long employeeId, long customerId, long serviceId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.employeeId = employeeId;
        this.customerId = customerId;
        this.serviceId = serviceId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isCancelled = false;
    }

    public long getId() {
        return id;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public long getServiceId() {
        return serviceId;
    }

    public void setServiceId(long serviceId) {
        this.serviceId = serviceId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getEmployeeFullName() {
        return employeeFullName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setEmployeeFullName(String employeeFullName) {
        this.employeeFullName = employeeFullName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    public String getCustomerFullName() {
        return customerFullName;
    }

    public void setCustomerFullName(String customerFullName) {
        this.customerFullName = customerFullName;
    }
}
