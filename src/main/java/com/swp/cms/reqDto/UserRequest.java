package com.swp.cms.reqDto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserRequest {
    private Integer id;
    @NotNull
    String display_name;
    String additional_info;
    @NotNull
    String email;
    @NotNull
    String password;
    String role_id;
    LocalDateTime createdDate;
}
