package com.swp.cms.controllers;

import com.swp.cms.dto.RequestDto;
import com.swp.cms.reqDto.RequestRequest;
import com.swp.entities.Request;
import com.swp.services.RequestService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/blog/request")
public class RequestController {
    private final RequestService requestService;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping("/GetAll")
    public List<RequestDto> getAll() {
        List<Request> requests = requestService.getAll();
        List<RequestDto> dtos = requests.stream()
                .map(request -> modelMapper.map(request, RequestDto.class))
                .collect(Collectors.toList());
        return dtos;
    }

    @GetMapping("/{id}")
    public RequestDto getRequestById(@PathVariable Integer id) {

        Request request = requestService.getById(id);
        RequestDto dto = modelMapper.map(request,RequestDto.class);
        return dto;
    }

    @PostMapping("/post")
    public RequestDto addRequest(@RequestBody RequestRequest requestRequest) {
//        Request request = modelMapper.map(requestRequest, Request.class);
        Request createdRequest = requestService.createRequest(requestRequest);
        RequestDto requestDto = modelMapper.map(createdRequest, RequestDto.class);
        return requestDto;
    }

    //Update a request by request id
    @PutMapping("/{requestId}")
    public RequestDto updateRequest(@PathVariable Integer requestId, @RequestBody RequestRequest requestRequest) {
        Request updatedRequest = requestService.updateRequest(requestId, requestRequest);
        RequestDto requestDto = modelMapper.map(updatedRequest, RequestDto.class);
        return requestDto;
    }
}