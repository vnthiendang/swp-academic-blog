package com.swp.cms.reqDto;

import com.swp.entities.Role;
import com.swp.entities.roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String displayName;
    private String email;
    private String password;
    private String additionalInfo;
    private Role role;
}
