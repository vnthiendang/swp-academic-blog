package com.swp.cms.reqDto;

import com.swp.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String displayName;
    private String email;
    private String password;
    private String additional_info;
    public String getRole_id() {
        return "Student";
    }
//    public void setRole_id(Role role_id) {
//        this.role_id = role_id;
//    }
}
