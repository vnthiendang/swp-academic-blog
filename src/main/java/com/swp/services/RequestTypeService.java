package com.swp.services;

import com.swp.cms.reqDto.RequestTypeRequest;
import com.swp.entities.RequestType;
import com.swp.repositories.RequestTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RequestTypeService {
    @Autowired
    private RequestTypeRepository requestTypeRepository;

    public RequestType getById(Integer id) {
        return requestTypeRepository.findById(id).orElseThrow();
    }

    public Boolean existsById(Integer id) {
        return requestTypeRepository.existsById(id);
    }

    public void deleteById(Integer id) {
        requestTypeRepository.deleteById(id);
    }

    public RequestType add(RequestType cate) {
        return requestTypeRepository.save(cate);
    }

    public List<RequestType> getAll() {
        return requestTypeRepository.findAll();
    }

    public RequestType createRequestType(RequestTypeRequest requestTypeRequest){
        RequestType requestType = new RequestType();
        requestType.setCreatedTime(LocalDateTime.now());
        requestType.setRequestInfo(requestTypeRequest.getRequestInfo());
        return requestTypeRepository.save(requestType);
    }
    public RequestType updateRequestType(Integer requestTypeID, RequestTypeRequest requestTypeRequest){
        RequestType requestType = getById(requestTypeID);
        requestType.setRequestInfo(requestTypeRequest.getRequestInfo());
        requestType.setCreatedTime(LocalDateTime.now());
        return requestTypeRepository.save(requestType); // Save and return the updated post
    }
}