package com.project.beautysalonreservationapi.DAL;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.beautysalonreservationapi.models.EmployeeAvailabilityPerService;

@Repository
public interface EmpAvailabilityRepository extends JpaRepository<EmployeeAvailabilityPerService,Long>{

	List<EmployeeAvailabilityPerService> findByServiceIdAndDateBetween(long serviceId, LocalDate start, LocalDate end);
}
