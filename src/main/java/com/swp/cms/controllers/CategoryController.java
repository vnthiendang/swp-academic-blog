package com.swp.cms.controllers;

import com.swp.cms.dto.CategoryDto;
import com.swp.cms.dto.CategoryDto;
import com.swp.cms.dto.VoteDto;
import com.swp.cms.mapper.CategoryMapper;
import com.swp.cms.reqDto.CategoryRequest;
import com.swp.entities.Category;
import com.swp.entities.Category;
import com.swp.repositories.CategoryRepository;
import com.swp.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/blog/category")
public class CategoryController {

    @Autowired
    private final CategoryService categoryService;
    @Autowired
    private ModelMapper modelMapper;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/getall")
    public List<CategoryDto> getAll() {
        List<Category> categories = categoryService.getAll();
        List<CategoryDto> categoryDtos = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());

        return categoryDtos;
    }

    @GetMapping("/{id}")
    public CategoryDto getCateById(@PathVariable Integer id) {
//                    System.out.println(" ID: hellosfdsdddddddddddddddddddddddddddddddddddddddddddddddd");
        Category cate = categoryService.getById(id);
//                    System.out.println(" ID: " + cate.getCateId());
//            System.out.println("Post ID: " + cate.getContent());
//            System.out.println("Status: " + cate.getParentCategory());
//            System.out.println("Created Date: " + cate.getCreatedDate());
//            System.out.println("sucesssssssssssssssssssssssssssDto");
        CategoryDto dto = modelMapper.map(cate, CategoryDto.class);
//                    System.out.println(" ID: " + dto.getId());
//            System.out.println("Post ID: " + dto.getContent());
//            System.out.println("Status: " + dto.getParentCategory());
//            System.out.println("Created Date: " + dto.getCreatedDate());
//            System.out.println("sucesssssssssssssssssssssssssssDto");
        return dto;
    }

    //create a category
    @PostMapping("/post")
    public CategoryDto addCategory(@RequestBody CategoryRequest categoryRequest) {
//        Category category = modelMapper.map(categoryRequest, Category.class);
        Category createdCategory = categoryService.createCategory(categoryRequest);
        CategoryDto categoryDto = modelMapper.map(createdCategory, CategoryDto.class);
        return categoryDto;
    }

    //Update a category by category id
    @PutMapping("/{categoryId}")
    public CategoryDto updateCategory(@PathVariable Integer categoryId, @RequestBody CategoryRequest categoryRequest) {
        Category updatedCategory = categoryService.updateCategory(categoryId, categoryRequest);
        CategoryDto categoryDto = modelMapper.map(updatedCategory, CategoryDto.class);
        return categoryDto;
    }
}
