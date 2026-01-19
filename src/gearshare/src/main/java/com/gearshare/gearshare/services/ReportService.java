package com.gearshare.gearshare.services;

import com.gearshare.gearshare.domain.dto.ReportDto;
import com.gearshare.gearshare.domain.entities.ReportEntity;

import java.util.List;
import java.util.UUID;

public interface ReportService {

    List<ReportEntity> findAllActiveReports();

    ReportEntity partiallyUpdateReport(UUID reportUUID, ReportEntity reportEntity);
}
