package com.swp.cms.controllers;

import com.swp.cms.dto.ReportViolationDto;
import com.swp.cms.reqDto.ReportViolationRequest;
import com.swp.entities.ReportViolation;
import com.swp.services.ReportViolationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/blog/reportViolation")
public class ReportViolationController {
    private final ReportViolationService reportViolationService;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired

    public ReportViolationController(ReportViolationService reportViolationService) {
        this.reportViolationService = reportViolationService;
    }

    @GetMapping("/GetAll")
    public List<ReportViolationDto> getAll() {
        List<ReportViolation> reportViolations = reportViolationService.getAll();
        List<ReportViolationDto> dtos = reportViolations.stream()
                .map(reportViolation -> modelMapper.map(reportViolation, ReportViolationDto.class))
                .collect(Collectors.toList());
        return dtos;
    }

    @GetMapping("/{id}")
    public ReportViolationDto getReportViolationById(@PathVariable Integer id) {

        ReportViolation reportViolation = reportViolationService.getById(id);
        ReportViolationDto dto = modelMapper.map(reportViolation,ReportViolationDto.class);
        return dto;
    }

    @PostMapping("/post")
    public ReportViolationDto addReportViolation(@RequestBody ReportViolationRequest reportViolationRequest) {
//        ReportViolation reportViolation = modelMapper.map(reportViolationRequest, ReportViolation.class);
        ReportViolation createdReportViolation = reportViolationService.createReportViolation(reportViolationRequest);
        ReportViolationDto reportViolationDto = modelMapper.map(createdReportViolation, ReportViolationDto.class);
        return reportViolationDto;
    }

}
