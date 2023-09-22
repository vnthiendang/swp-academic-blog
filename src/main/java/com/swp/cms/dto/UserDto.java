package com.swp.cms.dto;

import com.swp.entities.UserRole;
import lombok.Data;
import org.w3c.dom.Text;

import java.time.OffsetDateTime;
import java.util.Date;

@Data
public class UserDto {
    private Integer userId;
    private String display_name;
    private Text additional_info;
    private String password;
    private String email;
    private UserRole roleId;
    private OffsetDateTime created_date;
}
