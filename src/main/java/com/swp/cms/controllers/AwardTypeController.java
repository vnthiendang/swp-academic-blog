package com.swp.cms.controllers;
import com.swp.cms.dto.AwardTypeDto;
import com.swp.cms.mapper.AwardTypeMapper;
import com.swp.entities.AwardType;
import com.swp.repositories.AwardTypeRepository;
import com.swp.services.AwardTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/award_type")
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

    @GetMapping()
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
}
