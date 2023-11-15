package com.swp.cms.controllers;

import com.swp.cms.dto.RequestTypeDto;
import com.swp.cms.reqDto.RequestTypeRequest;
import com.swp.entities.RequestType;
import com.swp.services.RequestTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/blog/requestType")
public class RequestTypeController {
    private final RequestTypeService requestTypeService;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired

    public RequestTypeController(RequestTypeService requestTypeService) {
        this.requestTypeService = requestTypeService;
    }

    @GetMapping("/GetAll")
    public List<RequestTypeDto> getAll() {
        List<RequestType> requestTypes = requestTypeService.getAll();
        List<RequestTypeDto> dtos = requestTypes.stream()
                .map(requestType -> modelMapper.map(requestType, RequestTypeDto.class))
                .collect(Collectors.toList());
        return dtos;
    }

    @GetMapping("/{id}")
    public RequestTypeDto getRequestTypeById(@PathVariable Integer id) {

        RequestType requestType = requestTypeService.getById(id);
        RequestTypeDto dto = modelMapper.map(requestType,RequestTypeDto.class);
        return dto;
    }

    @PostMapping("/post")
    public RequestTypeDto addRequestType(@RequestBody RequestTypeRequest requestTypeRequest) {
//        RequestType requestType = modelMapper.map(requestTypeRequest, RequestType.class);
        RequestType createdRequestType = requestTypeService.createRequestType(requestTypeRequest);
        RequestTypeDto requestTypeDto = modelMapper.map(createdRequestType, RequestTypeDto.class);
        return requestTypeDto;
    }

    //Update a requestType by requestType id
    @PutMapping("/{requestTypeId}")
    public RequestTypeDto updateRequestType(@PathVariable Integer requestTypeId, @RequestBody RequestTypeRequest requestTypeRequest) {
        RequestType updatedRequestType = requestTypeService.updateRequestType(requestTypeId, requestTypeRequest);
        RequestTypeDto requestTypeDto = modelMapper.map(updatedRequestType, RequestTypeDto.class);
        return requestTypeDto;
    }
}