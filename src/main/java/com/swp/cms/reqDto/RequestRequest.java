package com.swp.cms.reqDto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RequestRequest {

    private Integer id;
    private Integer requestTypeId;
    private Integer requestedByUserId;
    private String requestDetail;
    private LocalDateTime createdTime;
    private String status;
    private Integer reviewedByAdminId;
    private LocalDateTime reviewedTime;
    private String adminMessage;

}