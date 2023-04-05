package com.project.beautysalonreservationapi.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Reservation {
    @Id
    private Long id;

    @ManyToOne()
    @JoinColumn(name ="customer_id")
    private Customer customer;

    @ManyToOne()
    @JoinColumn(name ="employee_id")
    private Employee employee;

    @ManyToOne()
    @JoinColumn(name ="service_id")
    private SalonService service;

    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    private boolean isCancelled;


    // Constructor(s)
    public Reservation() {
    }

    public Reservation(Employee employee, SalonService service, LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.employee = employee;
        this.service = service;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isCancelled = false;

    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public SalonService getService() {
        return service;
    }

    public void setService(SalonService service) {
        this.service = service;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }


}
