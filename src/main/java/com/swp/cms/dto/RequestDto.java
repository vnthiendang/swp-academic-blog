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
        if (requestedByUser != null) {
        return this.requestedByUser.getDisplay_name();
        }
        return "N/A";
    }

    public String getReviewedByAdmin() {
        if (reviewedByAdmin != null) {
            return this.reviewedByAdmin.getDisplay_name();
        }
        return null;
    }
}
