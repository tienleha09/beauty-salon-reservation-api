package com.project.beautysalonreservationapi.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "SERVICE")
public class SalonService {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private Long id;
	@NotNull
	private String name;
	@DecimalMin(value = "0.00", inclusive = false)
	private double price;
	private String description;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "employee_service",
			joinColumns = @JoinColumn(name ="service_id"),
			inverseJoinColumns = @JoinColumn(name = "employee_id")
	)
	@JsonIgnore
	private List<Employee> employees = new ArrayList<>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "service", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<EmployeeAvailabilityPerService> availability;

	@OneToMany(fetch = FetchType.LAZY,mappedBy = "service",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Reservation> reservations = new ArrayList<>();
	
	public SalonService() {}
	
	public SalonService(String name, String description, double price) {

		this.name = name;
		this.description = description;
		this.price = price;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public List<Employee> getEmployees() {
		return employees;
	}
	
	public void addEmployee(Employee e) {
		this.employees.add(e);
		e.getServices().add(this);
	}

	public void removeEmployee(Employee employee){
		this.employees.remove(employee);
		employee.getServices().remove(this);
	}


	@Override
	public String toString() {
		return "SalonService [id=" + id + ", name=" + name + ", price=" + price + ", description=" + description + "]";
	}

	
}
