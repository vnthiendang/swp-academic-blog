package com.swp.cms.reqDto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VoteTypeRequest {
    Integer id;
    @NotNull
    String voteType;
}
