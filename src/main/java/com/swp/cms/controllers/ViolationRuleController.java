package com.swp.cms.controllers;

import com.swp.cms.dto.ViolationRuleDto;
import com.swp.entities.ViolationRule;
import com.swp.services.ViolationRuleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/blog/violationRule")
public class ViolationRuleController {

    @Autowired
    private final ViolationRuleService violationRuleService;
    @Autowired
    private ModelMapper modelMapper;

    public ViolationRuleController(ViolationRuleService violationRuleService) {
        this.violationRuleService = violationRuleService;
    }

    @GetMapping("/GetAll")
    public List<ViolationRuleDto> getAll() {
        List<ViolationRule> categories = violationRuleService.getAll();
        List<ViolationRuleDto> violationRuleDtos = categories.stream()
                .map(violationRule -> modelMapper.map(violationRule, ViolationRuleDto.class))
                .collect(Collectors.toList());

        return violationRuleDtos;
    }

    @GetMapping("/{id}")
    public ViolationRuleDto getViolationRuleById(@PathVariable Integer id) {
        ViolationRule violationRule = violationRuleService.getById(id);
        ViolationRuleDto dto = modelMapper.map(violationRule, ViolationRuleDto.class);
        return dto;
    }

//    //create a violationRule
//    @PostMapping("/create")
//    public ViolationRuleDto addViolationRule(@RequestBody ViolationRuleRequest violationRuleRequest) {
//        ViolationRule createdViolationRule = violationRuleService.createViolationRule(violationRuleRequest);
//        ViolationRuleDto violationRuleDto = modelMapper.map(createdViolationRule, ViolationRuleDto.class);
//        return violationRuleDto;
//    }
//
//    //Update a violationRule by violationRule id
//    @PutMapping("/{violationRuleId}")
//    public ViolationRuleDto updateViolationRule(@PathVariable Integer violationRuleId, @RequestBody ViolationRuleRequest violationRuleRequest) {
//        ViolationRule updatedViolationRule = violationRuleService.updateViolationRule(violationRuleId, violationRuleRequest);
//        ViolationRuleDto violationRuleDto = modelMapper.map(updatedViolationRule, ViolationRuleDto.class);
//        return violationRuleDto;
//    }
}