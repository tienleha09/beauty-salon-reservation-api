package com.project.beautysalonreservationapi.util;

import com.project.beautysalonreservationapi.dto.CustomerReservationDto;
import com.project.beautysalonreservationapi.models.Reservation;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper extends GenericMapper<Reservation, CustomerReservationDto> {

    public ReservationMapper(ModelMapper modelMapper){
        super(modelMapper,Reservation.class,CustomerReservationDto.class);
    }


    @Override
    public void configureMappings() {

        TypeMap<Reservation,CustomerReservationDto> typeMap = this.getModelMapper().createTypeMap(Reservation.class, CustomerReservationDto.class);
        typeMap.addMapping(src ->src.getEmployee().getFullName(),CustomerReservationDto::setEmployeeFullName);
        typeMap.addMapping(src ->src.getEmployee().getId(),CustomerReservationDto::setEmployeeId);
        typeMap.addMapping(src ->src.getService().getName(),CustomerReservationDto::setServiceName);
        typeMap.addMapping(src ->src.getService().getId(),CustomerReservationDto::setServiceId);
        typeMap.addMapping(src ->src.getCustomer().getFullName(),CustomerReservationDto::setCustomerFullName);
        typeMap.addMapping(src->src.getCustomer().getId(),CustomerReservationDto::setCustomerId);
    }
}
