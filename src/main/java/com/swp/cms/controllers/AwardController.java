package com.swp.cms.controllers;

import com.swp.cms.dto.AwardDto;
import com.swp.cms.mapper.AwardMapper;
import com.swp.entities.Award;
import com.swp.repositories.AwardRepository;
import com.swp.services.AwardService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/blog/auth/award")
public class AwardController {
    private final AwardRepository awardRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AwardMapper mapper;
    @Autowired
    private AwardService awardService;

    public AwardController(AwardRepository awardRepository) {
        this.awardRepository = awardRepository;
    }

    @GetMapping()
    public List<AwardDto> getAll() {
        List<Award> awards = awardService.getAll();
        List<AwardDto> awardDtos = mapper.fromEntityToAwardDtoList(awards);
        return awardDtos;
    }

    @GetMapping("/{id}")
    public AwardDto getAwardById(@PathVariable Integer id) {

        Award award = awardService.getById(id);
        AwardDto dto = mapper.fromEntityToAwardDto(award);
        return dto;
    }
}
