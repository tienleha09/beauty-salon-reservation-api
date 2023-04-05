package com.project.beautysalonreservationapi.services;

import com.project.beautysalonreservationapi.DAL.EmployeeRepository;
import com.project.beautysalonreservationapi.DAL.ReservationRepository;
import com.project.beautysalonreservationapi.dto.CustomerReservationDto;
import com.project.beautysalonreservationapi.dto.EmployeeAppointmentDto;
import com.project.beautysalonreservationapi.exceptions.BadRequestException;
import com.project.beautysalonreservationapi.exceptions.EmployeeNotFoundException;
import com.project.beautysalonreservationapi.exceptions.InvalidTimeRangeException;
import com.project.beautysalonreservationapi.exceptions.SalonServiceNotFoundException;
import com.project.beautysalonreservationapi.models.Employee;
import com.project.beautysalonreservationapi.models.EmployeeSchedule;
import com.project.beautysalonreservationapi.models.Reservation;
import com.project.beautysalonreservationapi.models.SalonService;
import com.project.beautysalonreservationapi.util.EmpApptMapper;
import com.project.beautysalonreservationapi.util.ReservationMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.*;

@Service
public class ReservationServicesImp {
    private ReservationRepository _reservationRepo;

    private EmployeeRepository _employeeRepo;
    private ReservationMapper _reservationMapper;
    private EmpApptMapper _empApptMapper;

    public ReservationServicesImp(ReservationRepository _reservationRepo, ReservationMapper _reservationMapper,
        EmpApptMapper _empApptMapper, EmployeeRepository _employeeRepo) {
        this._reservationRepo = _reservationRepo;
        this._reservationMapper = _reservationMapper;
        this._empApptMapper = _empApptMapper;
        this._employeeRepo = _employeeRepo;
    }

    public List<CustomerReservationDto> getReservationByCustomerId(long customerId){
        List<Reservation> list =  this._reservationRepo.findByCustomerId(customerId);
        if(list.isEmpty()) return new ArrayList<>();
        return this._reservationMapper.toDtoList(list);
    }

    public List<CustomerReservationDto> getReservations(){
        List<Reservation> list =  this._reservationRepo.findAll(Sort.by("date").descending());
        if(list.isEmpty()) return new ArrayList<>();
        return this._reservationMapper.toDtoList(list);
    }

    public List<EmployeeAppointmentDto> getAppointmentByEmployeeId(long employeeId){
        List<Reservation> list =  this._reservationRepo.findByEmployeeId(employeeId);
        if(list.isEmpty()) return new ArrayList<>();
        return this._empApptMapper.toDtoList(list);
    }

    public List<EmployeeAppointmentDto> getAppointmentByEmployeeIdWithFilters(long employeeId, Map<String, String> queryParams) {
        List<Reservation> list =  this._reservationRepo.findByEmployeeIdAndFilters(employeeId,queryParams);
        if(list.isEmpty()) return new ArrayList<>();
        return this._empApptMapper.toDtoList(list);
    }

    public CustomerReservationDto createReservation(CustomerReservationDto customerReservationDto) {
        //validate start and end date
        LocalTime start = customerReservationDto.getStartTime();
        LocalTime end = customerReservationDto.getEndTime();
        if(start.isAfter(end)) throw new InvalidTimeRangeException(start,end);

        //check if given employee does the given service
        Employee employee = _employeeRepo.findById(customerReservationDto.getEmployeeId())
                .orElseThrow(()->new EmployeeNotFoundException(customerReservationDto.getEmployeeId()));

        SalonService service = employee.getServices().stream()
                .filter(s->s.getId().equals(customerReservationDto.getServiceId()))
                .findFirst().orElseThrow(()->new SalonServiceNotFoundException(customerReservationDto.getServiceId()));

        //check if the employee is working on provided date
        EmployeeSchedule schedule = employee.getEmployeeSchedules()
                .stream()
                .filter(employeeSchedule -> employeeSchedule.getDate().equals(customerReservationDto.getDate()))
                .findFirst().orElseThrow(()->new BadRequestException("Employee "+ employee.getFullName() + " does not work on " + customerReservationDto.getDate()));
        //check if the time is within employee working hour
        if(start.isBefore(schedule.getStartTime())|| end.isAfter(schedule.getEndTime())){
            throw new BadRequestException("The time range provided is not within working hours");
        }
        //check if reservation on the given time date already exists
        Map<String,String> query = new HashMap<>();
        query.put("date",schedule.getDate().toString());
        query.put("startTime",schedule.getStartTime().toString());
        query.put("endTime",schedule.getEndTime().toString());
        if(_reservationRepo.findByEmployeeIdAndFilters(employee.getId(),query).isEmpty()){
            //valid date, can add now
            Reservation reservation = _reservationMapper.toEntity(customerReservationDto);
            return _reservationMapper.toDto(_reservationRepo.save(reservation));
        }
        else{
            throw new BadRequestException("The provided date time is already taken. Provide another date.");
        }
    }
}
