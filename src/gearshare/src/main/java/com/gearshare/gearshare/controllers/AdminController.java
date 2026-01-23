package com.gearshare.gearshare.controllers;

import com.gearshare.gearshare.domain.dto.ReportDto;
import com.gearshare.gearshare.domain.dto.SuspensionDto;
import com.gearshare.gearshare.domain.entities.ReportEntity;
import com.gearshare.gearshare.domain.entities.SuspensionEntity;
import com.gearshare.gearshare.mappers.Mapper;
import com.gearshare.gearshare.services.ReportService;
import com.gearshare.gearshare.services.SuspensionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final ReportService reportService;

    private final Mapper<ReportEntity,ReportDto> reportMapper;

    private final SuspensionService suspensionService;

    private final Mapper<SuspensionEntity, SuspensionDto> suspensionMapper;

    public AdminController(ReportService reportService, Mapper<ReportEntity, ReportDto> reportMapper, SuspensionService suspensionService, Mapper<SuspensionEntity, SuspensionDto> suspensionMapper) {
        this.reportService = reportService;
        this.reportMapper = reportMapper;
        this.suspensionService = suspensionService;
        this.suspensionMapper = suspensionMapper;
    }

    @GetMapping( path = "/reports/active")
    private ResponseEntity<List<ReportDto>> getAllActiveReports(){

        List<ReportEntity> reports = reportService.findAllActiveReports();

        return new ResponseEntity<>(
                reports
                        .stream()
                        .map(reportMapper::mapTo)
                        .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @PatchMapping( path = "/reports/active/{reportUUID}")
    private ResponseEntity<ReportDto> updateReportStatus(
            @RequestBody ReportDto reportDto,
            @PathVariable("reportUUID") UUID reportUUID) {


        ReportEntity reportEntity = reportService.partiallyUpdateReport(reportUUID, reportMapper.mapFrom(reportDto));

        return new ResponseEntity<>(reportMapper.mapTo(reportEntity), HttpStatus.OK);
    }

    @GetMapping( path = "/suspensions/active")
    private ResponseEntity<List<SuspensionDto>> getAllActiveSuspensions(){


        List<SuspensionEntity> suspensions = suspensionService.findAllActiveSuspensions();

        return new ResponseEntity<>(
                suspensions
                        .stream()
                        .map(suspensionMapper::mapTo)
                        .collect(Collectors.toList()),
                HttpStatus.OK);
    }

}
