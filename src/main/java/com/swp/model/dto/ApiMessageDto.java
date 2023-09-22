package com.swp.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiMessageDto<T> {
    private Boolean result = true;
    private String code = null;
    private T data = null;
    private String message = null;
}
