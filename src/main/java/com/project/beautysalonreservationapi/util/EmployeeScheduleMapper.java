package com.project.beautysalonreservationapi.util;

import com.project.beautysalonreservationapi.dto.EmployeeScheduleDto;
import com.project.beautysalonreservationapi.models.EmployeeSchedule;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
public class EmployeeScheduleMapper extends GenericMapper<EmployeeSchedule,EmployeeScheduleDto> {


    public EmployeeScheduleMapper(ModelMapper modelMapper) {
        super(modelMapper, EmployeeSchedule.class, EmployeeScheduleDto.class);
    }

    @Override
    public void configureMappings() {
        TypeMap<EmployeeSchedule,EmployeeScheduleDto> typeMap = this.getModelMapper().createTypeMap(EmployeeSchedule.class,EmployeeScheduleDto.class);
        typeMap.addMapping(src ->src.getEmployee().getId(),EmployeeScheduleDto::setEmployeeId);
    }
}
