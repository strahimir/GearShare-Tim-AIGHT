package com.gearshare.gearshare.mappers.impl;

import com.gearshare.gearshare.config.GeometryUtils;
import com.gearshare.gearshare.domain.dto.AddressDto;
import com.gearshare.gearshare.domain.dto.CoordinatesDto;
import com.gearshare.gearshare.domain.entities.AddressEntity;
import com.gearshare.gearshare.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class AddressMapperImpl implements Mapper<AddressEntity, AddressDto> {


    private final ModelMapper modelMapper;

    public AddressMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        this.modelMapper
                .typeMap(AddressEntity.class, AddressDto.class)
                .addMappings( mapper ->
                        mapper
                                .skip(AddressDto::setCoordinates));

        this.modelMapper
                .typeMap(AddressDto.class, AddressEntity.class)
                .addMappings( mapper ->
                        mapper
                                .skip(AddressEntity::setCoordinates));

    }

    @Override
    public AddressDto mapTo(AddressEntity addressEntity) {

        AddressDto addressDto = modelMapper.map(addressEntity, AddressDto.class);

        if ( addressEntity.getCoordinates() != null )
            addressDto.setCoordinates(
                    new CoordinatesDto(
                            addressEntity.getCoordinates().getY(),
                            addressEntity.getCoordinates().getX()
                    )
            );

        return addressDto;

    }

    @Override
    public AddressEntity mapFrom(AddressDto addressDto) {

        AddressEntity addressEntity = modelMapper.map(addressDto, AddressEntity.class);

        if ( addressDto.getCoordinates() != null )
            addressEntity.setCoordinates(
                    GeometryUtils.toPoint(
                            addressDto.getCoordinates().getLatitude(),
                            addressDto.getCoordinates().getLongitude()
                    )
            );

        return  addressEntity;

    }
}

