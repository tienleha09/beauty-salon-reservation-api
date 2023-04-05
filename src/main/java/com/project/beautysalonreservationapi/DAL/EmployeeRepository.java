package com.project.beautysalonreservationapi.DAL;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.beautysalonreservationapi.models.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
	
	List<Employee> findEmployeesByServicesId(long serviceId);
}
