package com.swp.cms.controllers;

import com.swp.cms.dto.VoteTypeDto;
import com.swp.cms.reqDto.VoteTypeRequest;
import com.swp.entities.VoteType;
import com.swp.services.VoteTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/blog/votetype")
public class VoteTypeController {

    @Autowired
    private final VoteTypeService voteTypeService;
    @Autowired
    private ModelMapper modelMapper;

    public VoteTypeController(VoteTypeService voteTypeService) {
        this.voteTypeService = voteTypeService;
    }

    @GetMapping("/getall")
    public List<VoteTypeDto> getAll() {
        List<VoteType> categories = voteTypeService.getAll();
        List<VoteTypeDto> voteTypeDtos = categories.stream()
                .map(voteType -> modelMapper.map(voteType, VoteTypeDto.class))
                .collect(Collectors.toList());

        return voteTypeDtos;
    }

    @GetMapping("/{id}")
    public VoteTypeDto getVoteTypeById(@PathVariable Integer id) {
//                    System.out.println(" ID: hellosfdsdddddddddddddddddddddddddddddddddddddddddddddddd");
        VoteType voteType = voteTypeService.getById(id);
//                    System.out.println(" ID: " + cate.getCateId());
//            System.out.println("Post ID: " + cate.getContent());
//            System.out.println("Status: " + cate.getParentVoteType());
//            System.out.println("Created Date: " + cate.getCreatedDate());
//            System.out.println("sucesssssssssssssssssssssssssssDto");
        VoteTypeDto dto = modelMapper.map(voteType, VoteTypeDto.class);
//                    System.out.println(" ID: " + dto.getId());
//            System.out.println("Post ID: " + dto.getContent());
//            System.out.println("Status: " + dto.getParentVoteType());
//            System.out.println("Created Date: " + dto.getCreatedDate());
//            System.out.println("sucesssssssssssssssssssssssssssDto");
        return dto;
    }

    //create a voteType
    @PostMapping("/post")
    public VoteTypeDto addVoteType(@RequestBody VoteTypeRequest voteTypeRequest) {
//        VoteType voteType = modelMapper.map(voteTypeRequest, VoteType.class);
        VoteType createdVoteType = voteTypeService.createVoteType(voteTypeRequest);
        VoteTypeDto voteTypeDto = modelMapper.map(createdVoteType, VoteTypeDto.class);
        return voteTypeDto;
    }

    //Update a voteType by voteType id
    @PutMapping("/{voteTypeId}")
    public VoteTypeDto updateVoteType(@PathVariable Integer voteTypeId, @RequestBody VoteTypeRequest voteTypeRequest) {
        VoteType updatedVoteType = voteTypeService.updateVoteType(voteTypeId, voteTypeRequest);
        VoteTypeDto voteTypeDto = modelMapper.map(updatedVoteType, VoteTypeDto.class);
        return voteTypeDto;
    }
}