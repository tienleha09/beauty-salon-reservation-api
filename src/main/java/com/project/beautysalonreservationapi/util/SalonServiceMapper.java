package com.project.beautysalonreservationapi.util;

import com.project.beautysalonreservationapi.dto.SalonServiceDto;
import com.project.beautysalonreservationapi.models.SalonService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
public class SalonServiceMapper extends GenericMapper<SalonService,SalonServiceDto>{

    public SalonServiceMapper(ModelMapper modelMapper) {
        super(modelMapper, SalonService.class, SalonServiceDto.class);
    }

    @Override
    public void configureMappings() {
        TypeMap<SalonService,SalonServiceDto> typeMap = this.getModelMapper().createTypeMap(SalonService.class,SalonServiceDto.class);
    }
}
