package com.project.beautysalonreservationapi.util;


import com.project.beautysalonreservationapi.dto.EmployeeDto;
import com.project.beautysalonreservationapi.dto.ServiceIdNameDto;
import com.project.beautysalonreservationapi.models.Employee;
import com.project.beautysalonreservationapi.models.SalonService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeeMapper extends GenericMapper<Employee, EmployeeDto> {

    public EmployeeMapper(ModelMapper modelMapper){
        super(modelMapper,Employee.class,EmployeeDto.class);
    }


    @Override
    public void configureMappings() {

        this.getModelMapper().createTypeMap(SalonService.class, ServiceIdNameDto.class)
                .addMappings(mapper -> mapper.map(src -> src.getId(), ServiceIdNameDto::setId))
                .addMappings(mapper -> mapper.map(src -> src.getName(), ServiceIdNameDto::setName));

        this.getModelMapper().createTypeMap(Employee.class, EmployeeDto.class)
                .addMappings(mapper -> mapper.map(src ->{
                    List<ServiceIdNameDto> serviceDtos = new ArrayList<>();
                    List<SalonService> services = src.getServices();
                    if(services!=null){
                        serviceDtos = services.stream()
                                .map(s ->this.getModelMapper().map(s, ServiceIdNameDto.class))
                                .collect(Collectors.toList());
                    }
                    return serviceDtos;
                }, EmployeeDto::setServices));

    }
}
