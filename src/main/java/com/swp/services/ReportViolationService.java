package com.swp.services;

import com.swp.cms.reqDto.ReportViolationRequest;
import com.swp.entities.ReportViolation;
import com.swp.repositories.ReportRepository;
import com.swp.repositories.ReportViolationRepository;
import com.swp.repositories.ViolationRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportViolationService {
    @Autowired
    private ReportViolationRepository reportViolationRepository;
    @Autowired
    private ViolationRuleRepository violationRuleRepository;
    @Autowired
    private ReportRepository reportRepository;

    public ReportViolation getById(Integer id) {
        return reportViolationRepository.findById(id).orElseThrow();
    }

    public Boolean existsById(Integer id) {
        return reportViolationRepository.existsById(id);
    }

    public void deleteById(Integer id) {
        reportViolationRepository.deleteById(id);
    }

    public ReportViolation add(ReportViolation cate) {
        return reportViolationRepository.save(cate);
    }

    public List<ReportViolation> getAll() {
        return reportViolationRepository.findAll();
    }

    public ReportViolation createReportViolation(ReportViolationRequest reportViolationRequest){
        ReportViolation reportViolation = new ReportViolation();
        reportViolation.setViolationRule(violationRuleRepository.findById(reportViolationRequest.getViolationRuleId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Violation Rule")));
        reportViolation.setReport(reportRepository.findById(reportViolationRequest.getReportId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Report")));
        return reportViolationRepository.save(reportViolation);
    }
}