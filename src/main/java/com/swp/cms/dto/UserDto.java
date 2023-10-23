package com.swp.cms.dto;

import com.swp.entities.Role;
import lombok.Data;

import java.awt.*;
import java.time.LocalDateTime;

@Data
public class UserDto {
    private Integer userId;
    private String display_name;
    private String additional_info;
    private String password;
    private String email;
    private LocalDateTime created_date;
    private Role role_id;
    public String getRole_id(){
        return this.role_id.getRoleInfo();
    }
}
