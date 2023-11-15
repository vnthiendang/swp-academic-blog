package com.swp.cms.controllers;

import com.swp.cms.dto.ReportTypeDto;
import com.swp.cms.reqDto.ReportTypeRequest;
import com.swp.entities.ReportType;
import com.swp.services.ReportTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/blog/reportType")
public class ReportTypeController {
    private final ReportTypeService reportTypeService;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public ReportTypeController(ReportTypeService reportTypeService) {
        this.reportTypeService = reportTypeService;
    }

    @GetMapping("/GetAll")
    public List<ReportTypeDto> getAll() {
        List<ReportType> reportTypes = reportTypeService.getAll();
        List<ReportTypeDto> dtos = reportTypes.stream()
                .map(reportType -> modelMapper.map(reportType, ReportTypeDto.class))
                .collect(Collectors.toList());
        return dtos;
    }

    @GetMapping("/{id}")
    public ReportTypeDto getReportTypeById(@PathVariable Integer id) {

        ReportType reportType = reportTypeService.getById(id);
        ReportTypeDto dto = modelMapper.map(reportType,ReportTypeDto.class);
        return dto;
    }

    @PostMapping("/post")
    public ReportTypeDto addReportType(@RequestBody ReportTypeRequest reportTypeRequest) {
//        ReportType reportType = modelMapper.map(reportTypeRequest, ReportType.class);
        ReportType createdReportType = reportTypeService.createReportType(reportTypeRequest);
        ReportTypeDto reportTypeDto = modelMapper.map(createdReportType, ReportTypeDto.class);
        return reportTypeDto;
    }

    //Update a reportType by reportType id
    @PutMapping("/{reportTypeId}")
    public ReportTypeDto updateReportType(@PathVariable Integer reportTypeId, @RequestBody ReportTypeRequest reportTypeRequest) {
        ReportType updatedReportType = reportTypeService.updateReportType(reportTypeId, reportTypeRequest);
        ReportTypeDto reportTypeDto = modelMapper.map(updatedReportType, ReportTypeDto.class);
        return reportTypeDto;
    }
}