package com.project.beautysalonreservationapi.controllers;


import java.time.LocalDate;
import java.util.List;

import com.project.beautysalonreservationapi.dto.SalonServiceDto;
import com.project.beautysalonreservationapi.models.CustomConfirmationMessage;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.beautysalonreservationapi.dto.EmpAvailabilityPerServiceDto;
import com.project.beautysalonreservationapi.services.SalonServiceServicesImpl;

@RestController
@RequestMapping("/services")
public class SalonServicesController {
	
	private SalonServiceServicesImpl _salonServiceServices;

	public SalonServicesController(SalonServiceServicesImpl serviceImp) {
		this._salonServiceServices = serviceImp;
	}

	@GetMapping("/{id}")
	public ResponseEntity<SalonServiceDto> findById(@PathVariable long id){
		return ResponseEntity.ok(this._salonServiceServices.getService(id));
	}

	@GetMapping()
	public ResponseEntity<List<SalonServiceDto>> findAll(){
		return ResponseEntity.ok(this._salonServiceServices.getAllServices());
	}

	//Get /services/{serviceId}/availability?start={start}&end={end}
	@GetMapping("{serviceId}/availability")
	public ResponseEntity<?> getEmployeeAvailabilityByServiceAndDate(@PathVariable long serviceId,
			@RequestParam LocalDate start, @RequestParam LocalDate end){
		if(start.isAfter(end)) return ResponseEntity.badRequest().body("Start date must be before After date");
		List<EmpAvailabilityPerServiceDto> empAvail = this._salonServiceServices.getEmAvailabilityByServiceAndDate(serviceId,
				start, end);
		if(empAvail.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(empAvail);
	}

	@PostMapping
	public ResponseEntity<?> createService(@Valid @RequestBody SalonServiceDto serviceDto){

		SalonServiceDto service = this._salonServiceServices.createService(serviceDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(service);
	}

	@PutMapping("/{serviceId}")
	public ResponseEntity<?> updateService(@Valid @RequestBody SalonServiceDto newServiceDto, @PathVariable long serviceId){

		SalonServiceDto service = this._salonServiceServices.updateService(newServiceDto,serviceId);
		return ResponseEntity.status(HttpStatus.CREATED).body(service);
	}

	@DeleteMapping("/{serviceId}")
	public ResponseEntity<?> deleteService(@PathVariable long serviceId){

		CustomConfirmationMessage confirmationMessage = this._salonServiceServices.deleteService(serviceId);
		return ResponseEntity.status(HttpStatus.OK).body(confirmationMessage);
	}
}
