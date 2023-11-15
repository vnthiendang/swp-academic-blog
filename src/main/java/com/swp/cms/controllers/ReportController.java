package com.swp.cms.controllers;

import com.swp.cms.dto.ReportDto;
import com.swp.cms.reqDto.ReportRequest;
import com.swp.entities.Report;
import com.swp.repositories.ReportRepository;
import com.swp.services.ReportService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/blog/report")
public class ReportController {
    private final ReportRepository reportRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ReportService reportService;

    public ReportController(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @GetMapping("/GetAll")
    public List<ReportDto> getAll() {
        List<Report> cate = reportService.getAll();
        List<ReportDto> reportDtos = cate.stream()
                .map(report -> modelMapper.map(report, ReportDto.class))
                .collect(Collectors.toList());
        //return makeResponse(true, testingDto, "Get testing detail successful!");
        return reportDtos;
    }

    @GetMapping("/{id}")
    public ReportDto getCateById(@PathVariable Integer id) {
        Report report = reportService.getById(id);
        System.out.println(report.getViolationRules());
        ReportDto reportDto = modelMapper.map(report, ReportDto.class);
        return reportDto;
    }
    @PostMapping("/post")
    public ReportDto addReport(@RequestBody ReportRequest reportRequest) {
        Report createdReport = reportService.createReport(reportRequest);
        ReportDto reportDto = modelMapper.map(createdReport, ReportDto.class);
        return reportDto;
    }

    //Update a report by report id
    @PutMapping("/{reportId}")
    public ReportDto updateReport(@PathVariable Integer reportId, @RequestBody ReportRequest reportRequest) {
        Report updatedReport = reportService.updateReport(reportId, reportRequest);
        ReportDto reportDto = modelMapper.map(updatedReport, ReportDto.class);
        return reportDto;
    }
}