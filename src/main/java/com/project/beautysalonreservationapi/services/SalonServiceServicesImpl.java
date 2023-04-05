package com.project.beautysalonreservationapi.services;

import com.project.beautysalonreservationapi.DAL.EmpAvailabilityRepository;
import com.project.beautysalonreservationapi.DAL.SalonServiceRepository;
import com.project.beautysalonreservationapi.dto.EmpAvailabilityPerServiceDto;
import com.project.beautysalonreservationapi.dto.SalonServiceDto;
import com.project.beautysalonreservationapi.exceptions.EmployeeNotFoundException;
import com.project.beautysalonreservationapi.exceptions.SalonServiceNotFoundException;
import com.project.beautysalonreservationapi.models.*;
import com.project.beautysalonreservationapi.util.EmpAvailMapper;
import com.project.beautysalonreservationapi.util.SalonServiceMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SalonServiceServicesImpl {
	
	private SalonServiceRepository _serviceRepo;
	private SalonServiceMapper _salonServiceMapper;

	private EmpAvailMapper _empAvailMapper;
	private EmpAvailabilityRepository _empAvailRepo;
	
	public SalonServiceServicesImpl(SalonServiceRepository _serviceRepo, SalonServiceMapper _salonServiceMapper
			,EmpAvailMapper empAvailMapper, EmpAvailabilityRepository empAvailRepo) {
		this._serviceRepo = _serviceRepo;
		this._salonServiceMapper = _salonServiceMapper;
		this._empAvailMapper = empAvailMapper;
		this._empAvailRepo = empAvailRepo;

	}

	public List<SalonServiceDto> getAllServices(){
		return this._salonServiceMapper.toDtoList(this._serviceRepo.findAll());
	}

	public SalonServiceDto getService(long id){
		Optional<SalonService> service = this._serviceRepo.findById(id);
		if(service.isPresent()) return this._salonServiceMapper.toDto(service.get());
		throw new SalonServiceNotFoundException(id);
	}

	public List<EmpAvailabilityPerServiceDto> getEmAvailabilityByServiceAndDate(long serviceId, LocalDate start, LocalDate end){
		List<EmployeeAvailabilityPerService> list =  this._empAvailRepo.findByServiceIdAndDateBetween(serviceId, start, end);
		if(list.isEmpty())return new ArrayList<>();
		List<EmpAvailabilityPerServiceDto> empAvailDtoList = list.stream().map(av-> _empAvailMapper.toDto(av))
				.toList();
		return empAvailDtoList;
		
	}

	public SalonServiceDto createService(SalonServiceDto newServiceDto){
		SalonService service = this._salonServiceMapper.toEntity(newServiceDto);
		return this._salonServiceMapper.toDto(this._serviceRepo.save(service));
	}

	public SalonServiceDto updateService(SalonServiceDto serviceDto, long id) {

		Optional<SalonService> serviceOptional = this._serviceRepo.findById(id);
		SalonService service = this._salonServiceMapper.toEntity(serviceDto);
		if (serviceOptional.isEmpty()) {
			service.setId(id);
		}
		return this._salonServiceMapper.toDto(_serviceRepo.save(service));
	}

	public CustomConfirmationMessage deleteService(long id){
		Optional<SalonService> emp = this._serviceRepo.findById(id);
		if(emp.isPresent()){
			this._serviceRepo.deleteById(id);
			return new CustomConfirmationMessage("Service with id "+ id +" is deleted successfully", OperationStatus.Succeeded);
		}
		throw new EmployeeNotFoundException(id);

	}
	
}
