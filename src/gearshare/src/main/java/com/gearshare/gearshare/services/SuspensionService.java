package com.gearshare.gearshare.services;

import com.gearshare.gearshare.domain.entities.SuspensionEntity;

import java.util.List;

public interface SuspensionService {
    List<SuspensionEntity> findAllActiveSuspensions();
}
