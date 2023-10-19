package com.swp.cms.reqDto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AwardTypeRequest {
    Integer id;
    @NotNull
    String awardType;
}
