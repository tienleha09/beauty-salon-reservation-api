package com.project.beautysalonreservationapi.DAL;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.beautysalonreservationapi.models.SalonService;

@Repository
public interface SalonServiceRepository extends JpaRepository<SalonService, Long>{
	
	
}
