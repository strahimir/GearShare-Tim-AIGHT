package com.gearshare.gearshare.mappers.impl;

import com.gearshare.gearshare.domain.dto.ListingDto;
import com.gearshare.gearshare.domain.entities.immutable.AvailableListingEntity;
import com.gearshare.gearshare.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AvailableListingMapperImpl implements Mapper<AvailableListingEntity, ListingDto> {

    private final ModelMapper modelMapper;

    public AvailableListingMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ListingDto mapTo(AvailableListingEntity availableListingEntity) {
        return modelMapper.map(availableListingEntity, ListingDto.class);
    }

    @Override
    public AvailableListingEntity mapFrom(ListingDto listingDto) {
        return modelMapper.map(listingDto, AvailableListingEntity.class);
    }
}
