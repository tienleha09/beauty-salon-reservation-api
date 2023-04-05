package com.project.beautysalonreservationapi.util;

import com.project.beautysalonreservationapi.dto.EmployeeAppointmentDto;
import com.project.beautysalonreservationapi.models.Reservation;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
public class EmpApptMapper extends GenericMapper<Reservation, EmployeeAppointmentDto> {

    public EmpApptMapper(ModelMapper modelMapper){
        super(modelMapper,Reservation.class,EmployeeAppointmentDto.class);
    }


    @Override
    public void configureMappings() {

        TypeMap<Reservation,EmployeeAppointmentDto> typeMap = this.getModelMapper().createTypeMap(Reservation.class, EmployeeAppointmentDto.class);
        typeMap.addMapping(src ->src.getService().getName(),EmployeeAppointmentDto::setServiceName);
        typeMap.addMapping(src ->src.getCustomer().getFullName(),EmployeeAppointmentDto:: setCustomerFullName);
    }
}
