package com.swp.cms.dto;

import com.swp.entities.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RequestDto {
    private Integer id;
    private RequestType requestType;
    private User requestedByUser;
    private String requestDetail;
    private LocalDateTime createdTime;
    private String status;
    private User reviewedByAdmin;
    private LocalDateTime reviewedTime;
    private String adminMessage;

    public String getRequestType() {
        return this.requestType.getRequestInfo();
    }

    public String getRequestedByUser() {
        return this.requestedByUser.getDisplay_name();
    }

    public String getReviewedByAdmin() {
        return this.reviewedByAdmin.getDisplay_name();
    }
}
