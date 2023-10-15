package com.swp.cms.controllers;

import com.swp.services.PostApprovalsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/blog/postapproval")
public class PostApprovalController {
    private final PostApprovalsService postApprovalsService;
    @Autowired
    private ModelMapper modelMapper;

    public PostApprovalController(PostApprovalsService postApprovalsService) {
        this.postApprovalsService = postApprovalsService;
    }


}
