package com.project.beautysalonreservationapi.services;

import com.project.beautysalonreservationapi.DAL.EmployeeScheduleRepository;
import com.project.beautysalonreservationapi.DAL.SalonServiceRepository;
import com.project.beautysalonreservationapi.dto.EmpAvailabilityPerServiceDto;
import com.project.beautysalonreservationapi.dto.SalonServiceDto;
import com.project.beautysalonreservationapi.exceptions.EmployeeNotFoundException;
import com.project.beautysalonreservationapi.exceptions.SalonServiceNotFoundException;
import com.project.beautysalonreservationapi.models.*;
import com.project.beautysalonreservationapi.util.SalonServiceMapper;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SalonServiceServicesImpl {

	private SalonServiceRepository _serviceRepo;
	private SalonServiceMapper _salonServiceMapper;
	private EmployeeScheduleRepository employeeScheduleRepository;


	public SalonServiceServicesImpl(SalonServiceRepository _serviceRepo, SalonServiceMapper _salonServiceMapper
			,EmployeeScheduleRepository employeeScheduleRepository
			) {

		this._serviceRepo = _serviceRepo;
		this._salonServiceMapper = _salonServiceMapper;
		this.employeeScheduleRepository = employeeScheduleRepository;

	}

	public List<SalonServiceDto> getAllServices(){
		return this._salonServiceMapper.toDtoList(this._serviceRepo.findAll());
	}

	public SalonServiceDto getService(long id){
		Optional<SalonService> service = this._serviceRepo.findById(id);
		if(service.isPresent()) return this._salonServiceMapper.toDto(service.get());
		throw new SalonServiceNotFoundException(id);
	}

	public List<EmpAvailabilityPerServiceDto> getEmAvailabilityByServiceAndDate(long serviceId, LocalDate startDate, LocalDate endDate){

		List<EmpAvailabilityPerServiceDto> empAvailDtoList = new ArrayList<>();

		//check if serviceId is valid
		SalonService service = _serviceRepo.findById(serviceId).orElseThrow(()->new SalonServiceNotFoundException(serviceId));

		/*I would do a query like this:
		*	Select * from employee_schedule es, employee_service ser
		* 	where es.date >= startDate
		*  	and es.date <= endDate
		*  	and es.employee_id =  ser.employee_id
		* 	and ser.service_id = serviceId;
		* -> this returns a list of schedules of employees working on given date and can do the given service
		* */

		List<EmployeeSchedule> employeeScheduleList = employeeScheduleRepository.findByServiceAndDateRange(serviceId,startDate,endDate);

		//get the total time slots for each employee schedule

		for(EmployeeSchedule schedule: employeeScheduleList){
			LocalTime startTime = schedule.getStartTime();
			LocalTime endTime = schedule.getEndTime();
			long totalSlots = Duration.between(startTime,endTime).toMinutes()/30; //1 slot is 30 minutes

			//get reservations of that employee
			List<Reservation> reservationsPerEmployee = schedule.getEmployee().getReservations()
					.stream().filter(r ->r.getDate().equals(schedule.getDate())).toList();

			for(Reservation reservation:reservationsPerEmployee){
				LocalTime start = reservation.getStartTime();
				LocalTime end = reservation.getEndTime();
				long reservedSlots = Duration.between(start,end).toMinutes()/30; //get #of slots that a single reservation takes
				totalSlots -= reservedSlots;
			}
			//now we get # of free slots left, can populate a list of available start time
			List<LocalTime> freeSlots = new ArrayList<>();
			LocalTime time = schedule.getStartTime();
			while(totalSlots >0 && !(time.plusMinutes(30).isAfter(schedule.getEndTime()))){

				LocalTime finalTime = time;
				if(reservationsPerEmployee.stream().noneMatch(r -> !finalTime.isBefore(r.getStartTime()) && !finalTime.plusMinutes(30).isAfter(r.getEndTime()))){
					freeSlots.add(time);
					totalSlots --;
				}
				time = time.plusMinutes(30);
			}
			EmpAvailabilityPerServiceDto availabilityPerServiceDto = new EmpAvailabilityPerServiceDto(serviceId,schedule.getEmployee().getId(),schedule.getDate(),freeSlots);
			availabilityPerServiceDto.setServiceName(service.getName());
			availabilityPerServiceDto.setEmployeeFullName(schedule.getEmployee().getFullName());
			empAvailDtoList.add(availabilityPerServiceDto);
		}


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
