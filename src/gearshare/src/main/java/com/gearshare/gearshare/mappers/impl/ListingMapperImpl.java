package com.gearshare.gearshare.mappers.impl;

import com.gearshare.gearshare.domain.dto.ListingDto;
import com.gearshare.gearshare.domain.entities.ListingEntity;
import com.gearshare.gearshare.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ListingMapperImpl implements Mapper<ListingEntity, ListingDto> {

    private final ModelMapper modelMapper;

    public ListingMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ListingDto mapTo(ListingEntity listingEntity) {
        return modelMapper.map(listingEntity, ListingDto.class);
    }

    @Override
    public ListingEntity mapFrom(ListingDto listingDto) {
        return modelMapper.map(listingDto, ListingEntity.class);
    }
}
