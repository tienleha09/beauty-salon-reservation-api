package com.project.beautysalonreservationapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.beautysalonreservationapi.helpers.BusinessHours;
import jakarta.validation.constraints.FutureOrPresent;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

public class EmpAvailabilityPerServiceDto {
	private long serviceId;
	private String serviceName;
	private long employeeId;
	private String employeeFullName;
	@FutureOrPresent
	private LocalDate date;

	private int durationInHours =1;
	@BusinessHours
	@JsonFormat(pattern = "HH:mm")
	private List<LocalTime> timeSlots;

	public EmpAvailabilityPerServiceDto(long serviceId, long employeeId, LocalDate date, List<LocalTime> timeSlots) {
		this.serviceId = serviceId;
		this.employeeId = employeeId;
		this.date = date;
		this.timeSlots = timeSlots;
		this.durationInHours = 1;
	}

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

	public int getDurationInHours() {
		return durationInHours;
	}

	public void setDurationInHours(int durationInHours) {
		this.durationInHours = durationInHours;
	}

	public List<LocalTime> getTimeSlots() {
		return timeSlots;
	}

	@Override
	public String toString() {
		return "EmpAvailabilityPerServiceDto{" +
				"serviceId=" + serviceId +
				", serviceName='" + serviceName + '\'' +
				", employeeId=" + employeeId +
				", employeeFullName='" + employeeFullName + '\'' +
				", date=" + date +
				", durationInHours=" + durationInHours +
				", timeSlots=" + timeSlots.toString() +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		EmpAvailabilityPerServiceDto that = (EmpAvailabilityPerServiceDto) o;
		return serviceId == that.serviceId && employeeId == that.employeeId && durationInHours == that.durationInHours && Objects.equals(serviceName, that.serviceName) && Objects.equals(employeeFullName, that.employeeFullName) && Objects.equals(date, that.date) && Objects.equals(timeSlots, that.timeSlots);
	}

	@Override
	public int hashCode() {
		return Objects.hash(serviceId, serviceName, employeeId, employeeFullName, date, durationInHours, timeSlots);
	}
}
