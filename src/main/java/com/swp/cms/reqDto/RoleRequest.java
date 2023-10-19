package com.swp.cms.reqDto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoleRequest {
    Integer id;
    @NotNull
    String roleInfo;
}
