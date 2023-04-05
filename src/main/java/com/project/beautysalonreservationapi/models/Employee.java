package com.project.beautysalonreservationapi.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="EMPLOYEE")
public class Employee {
	@Id
	@GeneratedValue
	private long id;
	@NotNull
	@Column(name = "first_name",nullable = false)
	@Size(min =2, max = 25, message = "Firstname must be between 2-25 characters")
	private String firstName;
	@NotNull
	@Column(name = "last_name", nullable = false)
	@Size(min =2, max = 25, message = "Lastname must be between 2-25 characters")
	private String lastName;
	//@Pattern(regexp = "^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$",
	//message = "Phone number must be a valid 10 digits number")
	private String phone;
	@Email(message = "Must be a valid email address")
	private String email;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "employees")
	@JsonIgnore
	private List<SalonService> services = new ArrayList<>();
	@OneToMany(mappedBy = "employee",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Reservation> reservations = new ArrayList<>();

	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<EmployeeAvailabilityPerService> availabilityPerServices;

	@OneToMany(mappedBy = "employee",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JsonIgnore
	private Set<EmployeeSchedule> employeeSchedules;
	public Employee() {}
	
	public Employee(String firstName, String lastName, String phone, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.email = email;
		this.services = new ArrayList<>();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getFullName() {
		return this.firstName +" " + this.lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<SalonService> getServices() {
		return services;
	}
	
	public void doService(SalonService s) {
		this.services.add(s);
	}

	@PreRemove
	private void removeServiceAssociation(){
		for(SalonService service : this.services){
			service.getEmployees().remove(this);
		}
	}

	public Set<EmployeeSchedule> getEmployeeSchedules() {
		return employeeSchedules;
	}

	public void setEmployeeSchedules(Set<EmployeeSchedule> employeeSchedules) {
		this.employeeSchedules = employeeSchedules;
	}
}
