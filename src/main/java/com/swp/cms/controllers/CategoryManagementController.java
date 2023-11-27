package com.swp.cms.controllers;

import com.swp.cms.dto.CategoryManagementDto;
import com.swp.cms.reqDto.CategoryManagementRequest;
import com.swp.entities.CategoryManagement;
import com.swp.services.CategoryManagementService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
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
    public List<CategoryManagementDto> getAll(@RequestParam(name = "userId", required = false) Integer userId) {
        List<CategoryManagement> categoryManagements = categoryManagementService.getAll();
        if (userId != null){
            categoryManagements = categoryManagementService.GetCategoryManagementsByUserId(categoryManagements, userId);
        }

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
    public ResponseEntity<?> addCategoryManagement(@RequestBody CategoryManagementRequest categoryManagementRequest) {
        try {
            CategoryManagement createdCategoryManagement = categoryManagementService.createCategoryManagement(categoryManagementRequest);
            CategoryManagementDto categoryManagementDto = modelMapper.map(createdCategoryManagement, CategoryManagementDto.class);
            return ResponseEntity.ok(categoryManagementDto);
        } catch (IllegalArgumentException e) {
            // Handle the case where the request parameters are invalid
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request parameters: " + e.getMessage());
        } catch (Exception e) {
            // Handle other exceptions, such as database errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the category management" + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategoryManagementById(@PathVariable Integer id) {
        try {
            // Call the service to delete the category management entry
            categoryManagementService.deleteById(id);
            return new ResponseEntity<>("Category Management deleted successfully", HttpStatus.OK);
        } catch (NoSuchElementException e) {
            // Handle case where the category management entry with the given ID was not found
            return new ResponseEntity<>("Category Management not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Handle other exceptions, such as database errors
            return new ResponseEntity<>("An error occurred while deleting the Category Management", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}