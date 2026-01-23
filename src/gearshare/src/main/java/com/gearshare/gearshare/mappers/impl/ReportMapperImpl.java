package com.gearshare.gearshare.mappers.impl;

import com.gearshare.gearshare.domain.dto.ReportDto;
import com.gearshare.gearshare.domain.entities.ReportEntity;
import com.gearshare.gearshare.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ReportMapperImpl implements Mapper<ReportEntity, ReportDto> {

    private final ModelMapper modelMapper;

    public ReportMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ReportDto mapTo(ReportEntity reportEntity) {
        return modelMapper.map(reportEntity, ReportDto.class);
    }

    @Override
    public ReportEntity mapFrom(ReportDto reportDto) {
        return modelMapper.map(reportDto, ReportEntity.class);
    }
}
