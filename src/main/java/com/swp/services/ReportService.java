package com.swp.services;

import com.swp.cms.reqDto.ReportRequest;
import com.swp.entities.*;
import com.swp.repositories.ReportRepository;
import com.swp.repositories.ReportTypeRepository;
import com.swp.repositories.UserRepository;
import com.swp.repositories.ViolationRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private ReportTypeRepository reportTypeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ViolationRuleRepository violationRuleRepository;

    public Report getById(Integer id) {
        return reportRepository.findById(id).orElseThrow();
    }

    public Boolean existsById(Integer id) {
        return reportRepository.existsById(id);
    }

    public void deleteById(Integer id) {
        reportRepository.deleteById(id);
    }

    public Report add(Report cate) {
        return reportRepository.save(cate);
    }

    public List<Report> getAll() {
        return reportRepository.findAll();
    }

    public Report createReport(ReportRequest reportRequest){
        Report report = new Report();
        report.setReportType(reportTypeRepository.findById(reportRequest.getReportTypeId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Report Type")));
        report.setReportedByUser(userRepository.findById(reportRequest.getReportedByUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid User")));
        report.setReportDetail(reportRequest.getReportDetail());
        report.setCreatedTime(LocalDateTime.now());
        List<String> violationRuleList = reportRequest.getViolationRuleList();
        if (violationRuleList != null && !violationRuleList.isEmpty()) {
            List<ReportViolation> reportViolationList = new ArrayList<>();
            for (String violationRuleInfo : violationRuleList) {
                ViolationRule violationRule = violationRuleRepository.findByViolationRuleInfo(violationRuleInfo)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid Violation Rule"));
                ReportViolation reportViolation = new ReportViolation();
                reportViolation.setViolationRule(violationRule);
                reportViolation.setReport(report);
                reportViolationList.add(reportViolation);
            }
            report.setReportViolations(reportViolationList);
        }
        return reportRepository.save(report);
    }
    public Report updateReport(Integer reportID, ReportRequest reportRequest){
        Report report = getById(reportID);
        report.setReportDetail(reportRequest.getReportDetail());
        report.setCreatedTime(LocalDateTime.now());
        List<String> violationRuleList = reportRequest.getViolationRuleList();
        if (violationRuleList != null && !violationRuleList.isEmpty()) {
            report.getReportViolations().clear();
            List<ReportViolation> reportViolationList = new ArrayList<>();
            for (String violationRuleInfo : violationRuleList) {
                ViolationRule violationRule = violationRuleRepository.findByViolationRuleInfo(violationRuleInfo)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid Violation Rule"));
                ReportViolation reportViolation = new ReportViolation();
                reportViolation.setViolationRule(violationRule);
                reportViolation.setReport(report);
                reportViolationList.add(reportViolation);
            }
            report.setReportViolations(reportViolationList);
        }else {
            report.getReportViolations().clear();
        }
        return reportRepository.save(report); // Save and return the updated post
    }

    public List<Report> GetReportsByUserId(List<Report> cate, Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with userId: " + userId));
        return cate.stream()
                .filter(report -> Integer.parseInt(report.getReportedObjectLink())== userId)
                .collect(Collectors.toList());
    }
}