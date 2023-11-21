package com.swp.cms.reqDto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ReportRequest {

    private Integer id;
    private Integer reportTypeId;
    private Integer reportedByUserId;
    private LocalDateTime createdTime;
    @NotNull
    private String reportDetail;
    private String status;
    private Integer viewedByUser;
    private LocalDateTime reviewedTime;
    private String reportedObjectLink;
    private List<String> violationRuleList;
}