package com.project.beautysalonreservationapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.beautysalonreservationapi.helpers.BusinessHours;
import jakarta.validation.constraints.FutureOrPresent;

import java.time.LocalDate;
import java.time.LocalTime;

public class EmpAvailabilityPerServiceDto {
	private long serviceId;
	private String serviceName;
	private long employeeId;
	private String employeeFullName;
	@FutureOrPresent
	private LocalDate date;
	@BusinessHours
	@JsonFormat(pattern = "HH:mm")
	private LocalTime startTime;
	@BusinessHours
	@JsonFormat(pattern = "HH:mm")
	private LocalTime endTime;
	private boolean available;

	public long getServiceId() {
		return serviceId;
	}

	public void setServiceId(long serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeFullName() {
		return employeeFullName;
	}

	public void setEmployeeFullName(String employeeFullName) {
		this.employeeFullName = employeeFullName;
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

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}
}
