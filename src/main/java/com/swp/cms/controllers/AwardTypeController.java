package com.swp.cms.controllers;
import com.swp.cms.dto.AwardTypeDto;
import com.swp.cms.dto.AwardTypeDto;
import com.swp.cms.mapper.AwardTypeMapper;
import com.swp.cms.reqDto.AwardTypeRequest;
import com.swp.entities.AwardType;
import com.swp.entities.AwardType;
import com.swp.repositories.AwardTypeRepository;
import com.swp.services.AwardTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blog/award_type")
public class AwardTypeController {
    private final AwardTypeRepository awardTypeRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AwardTypeMapper mapper;
    @Autowired
    private AwardTypeService awardTypeService;

    public AwardTypeController(AwardTypeRepository awardTypeRepository) {
        this.awardTypeRepository = awardTypeRepository;
    }

    @GetMapping("/getall")
    public List<AwardTypeDto> getAll() {
        List<AwardType> awardTypes = awardTypeService.getAll();
        List<AwardTypeDto> dto = mapper.fromEntityToAwardTypeDtoList(awardTypes);
        return dto;
    }

    @GetMapping("/{id}")
    public AwardTypeDto getTypeById(@PathVariable Integer id) {

        AwardType type = awardTypeService.getById(id);
        AwardTypeDto dto = mapper.fromEntityToAwardTypeDto(type);
        return dto;
    }
    @PostMapping("/post")
    public AwardTypeDto addAwardType(@RequestBody AwardTypeRequest awardTypeRequest) {
        AwardType createdAwardType = awardTypeService.createAwardType(awardTypeRequest);
        AwardTypeDto awardTypeDto = modelMapper.map(createdAwardType, AwardTypeDto.class);
        return awardTypeDto;
    }

    //Update a awardType by awardType id
    @PutMapping("/{awardTypeId}")
    public AwardTypeDto updateAwardType(@PathVariable Integer awardTypeId, @RequestBody AwardTypeRequest awardTypeRequest) {
        AwardType updatedAwardType = awardTypeService.updateAwardType(awardTypeId, awardTypeRequest);
        AwardTypeDto awardTypeDto = modelMapper.map(updatedAwardType, AwardTypeDto.class);
        return awardTypeDto;
    }
}
