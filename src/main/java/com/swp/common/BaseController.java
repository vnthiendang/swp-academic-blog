package com.swp.common;

import com.swp.model.dto.ApiMessageDto;

public class BaseController {
    public ApiMessageDto<Object> makeResponse(Boolean result, Object data, String message){
        ApiMessageDto<Object> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setResult(result);
        apiMessageDto.setData(data);
        apiMessageDto.setMessage(message);
        return apiMessageDto;
    }
}
