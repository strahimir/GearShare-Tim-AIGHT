package com.gearshare.gearshare.services.impl;

import com.gearshare.gearshare.domain.entities.SuspensionEntity;
import com.gearshare.gearshare.repositories.SuspensionRepository;
import com.gearshare.gearshare.services.SuspensionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SuspensionServiceImpl implements SuspensionService {

    private final SuspensionRepository suspensionRepository;

    public SuspensionServiceImpl(SuspensionRepository suspensionRepository) {
        this.suspensionRepository = suspensionRepository;
    }


    @Override
    public List<SuspensionEntity> findAllActiveSuspensions() {
        return suspensionRepository.findAllBySuspensionLengthNotIn(List.of(-1));
    }
}
