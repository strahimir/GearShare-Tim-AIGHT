package com.gearshare.gearshare.mappers.impl;

import com.gearshare.gearshare.domain.entities.ClientEntity;
import com.gearshare.gearshare.domain.dto.ClientDto;
import com.gearshare.gearshare.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ClientMapperImpl implements Mapper<ClientEntity, ClientDto> {

    private final ModelMapper modelMapper;

    public ClientMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ClientDto mapTo(ClientEntity clientEntity){
        return modelMapper.map(clientEntity, ClientDto.class);
    }

    @Override
    public ClientEntity mapFrom(ClientDto clientDto){
        return modelMapper.map(clientDto, ClientEntity.class);
    }

}
