package com.swp.cms.controllers;

import com.swp.cms.dto.CategoryDto;
import com.swp.cms.mapper.CategoryMapper;
import com.swp.entities.Category;
import com.swp.repositories.CategoryRepository;
import com.swp.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/blog/category")
public class CategoryController {
    private final CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CategoryMapper mapper;
    @Autowired
    private CategoryService categoryService;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/GetAll")
    public List<CategoryDto> getAll() {
        List<Category> cate = categoryService.getAll();
        List<CategoryDto> categoryDtos = mapper.fromEntityToCategoryDtoList(cate);
        //return makeResponse(true, testingDto, "Get testing detail successful!");
        return categoryDtos;
    }

    @GetMapping("/{id}")
    public CategoryDto getCateById(@PathVariable Integer id) {

        Category cate = categoryService.getById(id);
        CategoryDto categoryDto = mapper.fromEntityToCategoryDto(cate);
        return categoryDto;
    }

}
