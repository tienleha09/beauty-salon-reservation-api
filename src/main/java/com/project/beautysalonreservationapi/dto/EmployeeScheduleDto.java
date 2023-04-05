package com.project.beautysalonreservationapi.dto;

import com.project.beautysalonreservationapi.helpers.BusinessHours;
import com.project.beautysalonreservationapi.models.EmployeeSchedule;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class EmployeeScheduleDto {
	@NotNull
	private long employeeId;

	@FutureOrPresent
	private LocalDate date;

	@NotNull
	@BusinessHours
	private LocalTime startTime;
	@NotNull
	@BusinessHours
	private LocalTime endTime;

	public EmployeeScheduleDto(){}

	public EmployeeScheduleDto(LocalDate date, LocalTime startTime, LocalTime endTime) {
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
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

	public long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}
}

