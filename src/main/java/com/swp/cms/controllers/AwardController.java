package com.swp.cms.controllers;

import com.swp.cms.dto.AwardDto;
import com.swp.cms.reqDto.AwardRequest;
import com.swp.entities.Award;
import com.swp.services.AwardService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
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
    @GetMapping("/getall/{postId}")
    public List<AwardDto> getAllByPostId(@PathVariable Integer postId) {
        List<Award> awards = awardService.getAll(); // Assuming this method returns all awards
        List<AwardDto> awardDtos = awards.stream()
                .filter(award -> Objects.equals(award.getPost().getPostsId(), postId))
                .map(award -> modelMapper.map(award, AwardDto.class))
                .collect(Collectors.toList());

        return awardDtos;
    }
    @GetMapping("/{id}")
    public AwardDto getAwardById(@PathVariable Integer id) {
        Award award = awardService.getById(id);
        AwardDto dto = modelMapper.map(award, AwardDto.class);
        return dto;
    }

//    //create a award
//    @PostMapping("/post")
//    public AwardDto addAward(@RequestBody AwardRequest awardRequest) {
//        Award createdAward = awardService.createAward(awardRequest);
//        AwardDto awardDto = modelMapper.map(createdAward, AwardDto.class);
//        return awardDto;
//    }
@PostMapping("/post")
public ResponseEntity<Object> addAward(@RequestBody AwardRequest awardRequest) {
    try {
        Award createdAward = awardService.createOrUpdateAward(awardRequest);

        if (createdAward != null) {
            AwardDto awardDto = modelMapper.map(createdAward, AwardDto.class);
            return ResponseEntity.ok(awardDto);
        } else {
            String resultMessage = "Failed to perform the award operation";
            return ResponseEntity.badRequest().body(resultMessage);
        }
    } catch (IllegalArgumentException e) {
        // Handle the specific exception thrown when invalid arguments are provided
        return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
        // Handle other general exceptions
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request");
    }
}


//    //Update a award by award id
//    @PutMapping("/{awardId}")
//    public AwardDto updateAward(@PathVariable Integer awardId, @RequestBody AwardRequest awardRequest) {
//        Award updatedAward = awardService.updateAward(awardId, awardRequest);
//        AwardDto awardDto = modelMapper.map(updatedAward, AwardDto.class);
//        return awardDto;
//    }
}