package com.swp.cms.controllers;

import com.swp.cms.dto.AwardDto;
import com.swp.cms.dto.AwardDto;
import com.swp.cms.mapper.AwardMapper;
import com.swp.cms.reqDto.AwardRequest;
import com.swp.entities.Award;
import com.swp.entities.Award;
import com.swp.repositories.AwardRepository;
import com.swp.services.AwardService;
import com.swp.services.AwardService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/blog/award")
public class AwardController {

    @Autowired
    private final AwardService awardService;
    @Autowired
    private ModelMapper modelMapper;

    public AwardController(AwardService awardService) {
        this.awardService = awardService;
    }

    @GetMapping("/getall")
    public List<AwardDto> getAll() {
        List<Award> categories = awardService.getAll();
        List<AwardDto> awardDtos = categories.stream()
                .map(award -> modelMapper.map(award, AwardDto.class))
                .collect(Collectors.toList());

        return awardDtos;
    }

    @GetMapping("/{id}")
    public AwardDto getAwardById(@PathVariable Integer id) {
//                    System.out.println(" ID: hellosfdsdddddddddddddddddddddddddddddddddddddddddddddddd");
        Award award = awardService.getById(id);
//                    System.out.println(" ID: " + cate.getCateId());
//            System.out.println("Post ID: " + cate.getContent());
//            System.out.println("Status: " + cate.getParentAward());
//            System.out.println("Created Date: " + cate.getCreatedDate());
//            System.out.println("sucesssssssssssssssssssssssssssDto");
        AwardDto dto = modelMapper.map(award, AwardDto.class);
//                    System.out.println(" ID: " + dto.getId());
//            System.out.println("Post ID: " + dto.getContent());
//            System.out.println("Status: " + dto.getParentAward());
//            System.out.println("Created Date: " + dto.getCreatedDate());
//            System.out.println("sucesssssssssssssssssssssssssssDto");
        return dto;
    }

    //create a award
    @PostMapping("/post")
    public AwardDto addAward(@RequestBody AwardRequest awardRequest) {
//        Award award = modelMapper.map(awardRequest, Award.class);
        Award createdAward = awardService.createAward(awardRequest);
        AwardDto awardDto = modelMapper.map(createdAward, AwardDto.class);
        return awardDto;
    }

    //Update a award by award id
    @PutMapping("/{awardId}")
    public AwardDto updateAward(@PathVariable Integer awardId, @RequestBody AwardRequest awardRequest) {
        Award updatedAward = awardService.updateAward(awardId, awardRequest);
        AwardDto awardDto = modelMapper.map(updatedAward, AwardDto.class);
        return awardDto;
    }
}