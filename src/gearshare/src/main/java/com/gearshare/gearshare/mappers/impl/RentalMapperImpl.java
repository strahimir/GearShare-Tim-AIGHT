package com.gearshare.gearshare.mappers.impl;

import com.gearshare.gearshare.domain.dto.RentalDto;
import com.gearshare.gearshare.domain.entities.RentalEntity;
import com.gearshare.gearshare.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class RentalMapperImpl implements Mapper<RentalEntity, RentalDto> {

    private final ModelMapper modelMapper;

    public RentalMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public RentalDto mapTo(RentalEntity rentalEntity){
        return modelMapper.map(rentalEntity, RentalDto.class);
    }

    @Override
    public RentalEntity mapFrom(RentalDto rentalDto){
        return modelMapper.map(rentalDto, RentalEntity.class);
    }

}
