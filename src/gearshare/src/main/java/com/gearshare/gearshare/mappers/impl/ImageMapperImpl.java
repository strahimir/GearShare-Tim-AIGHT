package com.gearshare.gearshare.mappers.impl;

import com.gearshare.gearshare.domain.dto.ImageDto;
import com.gearshare.gearshare.domain.entities.ImageEntity;
import com.gearshare.gearshare.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ImageMapperImpl implements Mapper<ImageEntity, ImageDto> {

    private final ModelMapper modelMapper;

    public ImageMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ImageDto mapTo(ImageEntity imageEntity) {
        return modelMapper.map(imageEntity, ImageDto.class);
    }

    @Override
    public ImageEntity mapFrom(ImageDto imageDto) {
        return modelMapper.map(imageDto, ImageEntity.class);
    }
}
