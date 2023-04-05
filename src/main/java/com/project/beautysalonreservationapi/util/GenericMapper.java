package com.project.beautysalonreservationapi.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;

import java.util.List;
import java.util.stream.Collectors;

public abstract class GenericMapper<S,D> {
    private final ModelMapper modelMapper;
    private final Class<S> sourceClass;

    private final Class<D> dtoClass;

    public GenericMapper(ModelMapper modelMapper, Class<S> sourceClass, Class<D> dto) {
        this.modelMapper = modelMapper;
        this.sourceClass = sourceClass;
        this.dtoClass = dto;
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
        configureMappings();
    }
    public abstract void configureMappings();

    public ModelMapper getModelMapper(){
        return this.modelMapper;
    }
    public S toEntity(D dto){
        return modelMapper.map(dto, sourceClass);
    }

    public D toDto(S entity){
        return modelMapper.map(entity,dtoClass);
    }

    public List<D> toDtoList(List<S> entityList) {
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<S> toEntityList(List<D> dtoList){
        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
