package com.gearshare.gearshare.services.impl;

import com.gearshare.gearshare.domain.dto.ReportDto;
import com.gearshare.gearshare.domain.entities.ReportEntity;
import com.gearshare.gearshare.repositories.ReportRepository;
import com.gearshare.gearshare.services.ReportService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public List<ReportEntity> findAllActiveReports() {
        return reportRepository.findAllByReviewDateTimeIsNull();
    }

    @Override
    public ReportEntity partiallyUpdateReport(UUID reportUUID, ReportEntity report) {
        return reportRepository.findById(reportUUID)
                .map(reportEntity -> {

                    Optional.ofNullable(reportEntity.getOutcome()).ifPresent(reportEntity::setOutcome);

                    return reportRepository.save(reportEntity);
                })
                .orElseThrow(() -> new RuntimeException(String.format("Cannot find report with UUID: [%s]", reportUUID)));
    }
}
