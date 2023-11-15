package com.swp.services;

import com.swp.cms.reqDto.RequestRequest;
import com.swp.entities.Request;
import com.swp.repositories.RequestRepository;
import com.swp.repositories.RequestTypeRepository;
import com.swp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RequestService {
    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private RequestTypeRepository requestTypeRepository;
    @Autowired
    private UserRepository userRepository;

    public Request getById(Integer id) {
        return requestRepository.findById(id).orElseThrow();
    }

    public Boolean existsById(Integer id) {
        return requestRepository.existsById(id);
    }

    public void deleteById(Integer id) {
        requestRepository.deleteById(id);
    }

    public Request add(Request cate) {
        return requestRepository.save(cate);
    }

    public List<Request> getAll() {
        return requestRepository.findAll();
    }

    public Request createRequest(RequestRequest requestRequest){
        Request request = new Request();
        request.setCreatedTime(LocalDateTime.now());
        request.setRequestDetail(requestRequest.getRequestDetail());
        request.setRequestType(requestTypeRepository.findById(requestRequest.getRequestTypeId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Request type")));
        request.setRequestedByUser(userRepository.findById(requestRequest.getRequestedByUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid User")));
        request.setStatus("pending");
        return requestRepository.save(request);
    }
    public Request updateRequest(Integer requestID, RequestRequest requestRequest){
        Request request = getById(requestID);
        request.setCreatedTime(LocalDateTime.now());
        request.setRequestDetail(requestRequest.getRequestDetail());
        return requestRepository.save(request); // Save and return the updated post
    }
    public Request reviewRequest(Integer requestID, RequestRequest requestRequest){
        Request request = getById(requestID);
        request.setReviewedTime(LocalDateTime.now());
        request.setReviewedByAdmin(userRepository.findById(requestRequest.getReviewedByAdminId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Admin")));
        request.setAdminMessage(requestRequest.getAdminMessage());
        request.setStatus(requestRequest.getStatus());
        return requestRepository.save(request); // Save and return the updated post
    }

}