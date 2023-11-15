package com.swp.services;

import com.swp.cms.reqDto.ReportTypeRequest;
import com.swp.entities.ReportType;
import com.swp.repositories.ReportTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportTypeService {
    @Autowired
    private ReportTypeRepository reportTypeRepository;

    public ReportType getById(Integer id) {
        return reportTypeRepository.findById(id).orElseThrow();
    }

    public Boolean existsById(Integer id) {
        return reportTypeRepository.existsById(id);
    }

    public void deleteById(Integer id) {
        reportTypeRepository.deleteById(id);
    }

    public ReportType add(ReportType cate) {
        return reportTypeRepository.save(cate);
    }

    public List<ReportType> getAll() {
        return reportTypeRepository.findAll();
    }

    public ReportType createReportType(ReportTypeRequest reportTypeRequest){
        ReportType reportType = new ReportType();
        reportType.setReportTypeInfo(reportTypeRequest.getRequestTypeInfo());
        return reportTypeRepository.save(reportType);
    }
    public ReportType updateReportType(Integer reportTypeID, ReportTypeRequest reportTypeRequest){
        ReportType reportType = getById(reportTypeID);
        reportType.setReportTypeInfo(reportTypeRequest.getRequestTypeInfo());
        return reportTypeRepository.save(reportType);
    }
}