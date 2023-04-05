package com.project.beautysalonreservationapi.services;

import com.project.beautysalonreservationapi.DAL.EmployeeRepository;
import com.project.beautysalonreservationapi.DAL.EmployeeScheduleRepository;
import com.project.beautysalonreservationapi.dto.EmployeeDto;
import com.project.beautysalonreservationapi.dto.EmployeeScheduleDto;
import com.project.beautysalonreservationapi.exceptions.EmployeeNotFoundException;
import com.project.beautysalonreservationapi.models.*;
import com.project.beautysalonreservationapi.util.EmployeeMapper;
import com.project.beautysalonreservationapi.util.EmployeeScheduleMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServicesImp {
    private EmployeeRepository employeeRepository;
    private EmployeeScheduleRepository employeeScheduleRepository;

    private EmployeeMapper employeeMapper;
    private EmployeeScheduleMapper employeeScheduleMapper;

    public EmployeeServicesImp(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper
        ,EmployeeScheduleMapper employeeScheduleMapper,EmployeeScheduleRepository employeeScheduleRepository) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.employeeScheduleMapper = employeeScheduleMapper;
        this.employeeScheduleRepository = employeeScheduleRepository;
    }

    public List<EmployeeDto> getAllEmployees(){
        List<Employee> list = this.employeeRepository.findAll();
        if(list.isEmpty()) return new ArrayList<>();
        return this.employeeMapper.toDtoList(list);
    }

    public EmployeeDto getEmployee(long id){
        Optional<Employee> emp = this.employeeRepository.findById(id);
        if(emp.isPresent()) return this.employeeMapper.toDto(emp.get());
        throw new EmployeeNotFoundException(id);
    }

    public EmployeeDto createEmployee(EmployeeDto newEmpDto){
        Employee emp = this.employeeMapper.toEntity(newEmpDto);
        return this.employeeMapper.toDto(this.employeeRepository.save(emp));
    }

    public EmployeeDto updateEmployee(EmployeeDto newEmpDto, long id) throws EmployeeNotFoundException{
        Optional<Employee> employeeOptional = this.employeeRepository.findById(id);
        if(employeeOptional.isPresent()){
            Employee emp = this.employeeMapper.toEntity(newEmpDto);
            return this.employeeMapper.toDto(employeeRepository.save(emp));
        }
        throw new EmployeeNotFoundException(id);

    }

    public CustomConfirmationMessage deleteEmployee(long id){
        Optional<Employee> emp = this.employeeRepository.findById(id);
        if(emp.isPresent()){
            this.employeeRepository.deleteById(id);
            return new CustomConfirmationMessage("Employee with id "+ id +" is deleted successfully", OperationStatus.Succeeded);
        }
        throw new EmployeeNotFoundException(id);

    }

    public CustomConfirmationMessage createEmployeeSchedule(long employeeId, List<EmployeeScheduleDto> employeeScheduleDtoList){

        Optional<Employee> emp = this.employeeRepository.findById(employeeId);
        if(emp.isPresent()){

            List<EmployeeSchedule> employeeScheduleList = employeeScheduleMapper.toEntityList(employeeScheduleDtoList);

            employeeScheduleRepository.saveAll(employeeScheduleList);
            StringBuilder dates = new StringBuilder();
            for (var schedule: employeeScheduleList){
                dates.append(schedule.getDate().toString() + ", ");
            }
            String dateList = dates.substring(0, dates.length()-2);
            String verb = employeeScheduleList.size()>1? " have":" has";

            return new CustomConfirmationMessage("Schedule on the following date: " + dateList + verb +
                    " been successfully created.", OperationStatus.Succeeded);
        }
        throw new EmployeeNotFoundException(employeeId);
    }
}
