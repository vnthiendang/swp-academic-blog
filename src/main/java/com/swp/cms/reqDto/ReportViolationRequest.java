package com.swp.cms.reqDto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ReportViolationRequest {

    private Integer id;
    private Integer violationRuleId;
    private Integer reportId;

}