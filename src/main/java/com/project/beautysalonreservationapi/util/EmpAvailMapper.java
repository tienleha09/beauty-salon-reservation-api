package com.project.beautysalonreservationapi.util;

import com.project.beautysalonreservationapi.dto.EmpAvailabilityPerServiceDto;
import com.project.beautysalonreservationapi.models.EmployeeAvailabilityPerService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
public class EmpAvailMapper extends GenericMapper<EmployeeAvailabilityPerService, EmpAvailabilityPerServiceDto> {

    public EmpAvailMapper(ModelMapper modelMapper){
        super(modelMapper, EmployeeAvailabilityPerService.class, EmpAvailabilityPerServiceDto.class);
    }


    @Override
    public void configureMappings() {

        TypeMap<EmployeeAvailabilityPerService, EmpAvailabilityPerServiceDto> typeMap = this.getModelMapper().createTypeMap(EmployeeAvailabilityPerService.class, EmpAvailabilityPerServiceDto.class);
        typeMap.addMapping(src ->src.getEmployee().getId(), EmpAvailabilityPerServiceDto::setEmployeeId);
        typeMap.addMapping(src ->src.getService().getId(), EmpAvailabilityPerServiceDto::setServiceId);
    }
}
