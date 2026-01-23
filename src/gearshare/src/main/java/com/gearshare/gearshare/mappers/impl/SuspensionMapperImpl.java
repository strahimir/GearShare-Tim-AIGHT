package com.gearshare.gearshare.mappers.impl;

import com.gearshare.gearshare.domain.dto.SuspensionDto;
import com.gearshare.gearshare.domain.entities.SuspensionEntity;
import com.gearshare.gearshare.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class SuspensionMapperImpl implements Mapper<SuspensionEntity, SuspensionDto> {
    
    private final ModelMapper modelMapper;

    public SuspensionMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public SuspensionDto mapTo(SuspensionEntity suspensionEntity) {
        return modelMapper.map(suspensionEntity, SuspensionDto.class);
    }

    @Override
    public SuspensionEntity mapFrom(SuspensionDto suspensionDto) {
        return modelMapper.map(suspensionDto, SuspensionEntity.class);
    }
}
