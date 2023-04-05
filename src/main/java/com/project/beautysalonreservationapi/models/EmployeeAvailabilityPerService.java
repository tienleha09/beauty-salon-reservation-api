package com.project.beautysalonreservationapi.models;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/*
* This class is model for emp avail for a specific service with time
*
*
* */
@Entity
@Table(name= "EMPLOYEE_AVAILABILITY")
public class EmployeeAvailabilityPerService {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@ManyToOne
	@JoinColumn(name="employee_id")	
	private Employee employee;
	
	@ManyToOne
	@JoinColumn(name = "service_id")
	private SalonService service;
	
	private LocalDate date;
	private LocalTime startTime;
	private LocalTime endTime;
	private boolean isAvailable;
	
//	public EmployeeAvailability(long empId, long serviceId, LocalDate date, LocalTime startTime, LocalTime endTime
//			,boolean isAvailable) {
//		
//		this.date = date;
//		this.startTime = startTime;
//		this.endTime = endTime;
//		this.isAvailable = isAvailable;
//	}

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

	public long getId() {
		return id;
	}

	public Employee getEmployee() {
		return employee;
	}

	public SalonService getService() {
		return service;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	
	
	
}
