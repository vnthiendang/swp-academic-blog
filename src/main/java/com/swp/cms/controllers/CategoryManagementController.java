package com.swp.cms.controllers;

import com.swp.cms.dto.CategoryManagementDto;
import com.swp.cms.reqDto.CategoryManagementRequest;
import com.swp.entities.CategoryManagement;
import com.swp.services.CategoryManagementService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/blog/categoryManagement")
public class CategoryManagementController {
    private final CategoryManagementService categoryManagementService;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public CategoryManagementController(CategoryManagementService categoryManagementService) {
        this.categoryManagementService = categoryManagementService;
    }

    @GetMapping("/GetAll")
    public List<CategoryManagementDto> getAll() {
        List<CategoryManagement> categoryManagements = categoryManagementService.getAll();
        List<CategoryManagementDto> dtos = categoryManagements.stream()
                .map(categoryManagement -> modelMapper.map(categoryManagement, CategoryManagementDto.class))
                .collect(Collectors.toList());
        return dtos;
    }

    @GetMapping("/{id}")
    public CategoryManagementDto getCategoryManagementById(@PathVariable Integer id) {

        CategoryManagement categoryManagement = categoryManagementService.getById(id);
        CategoryManagementDto dto = modelMapper.map(categoryManagement,CategoryManagementDto.class);
        return dto;
    }

    @PostMapping("/post")
    public CategoryManagementDto addCategoryManagement(@RequestBody CategoryManagementRequest categoryManagementRequest) {
        CategoryManagement createdCategoryManagement = categoryManagementService.createCategoryManagement(categoryManagementRequest);
        CategoryManagementDto categoryManagementDto = modelMapper.map(createdCategoryManagement, CategoryManagementDto.class);
        return categoryManagementDto;
    }

}