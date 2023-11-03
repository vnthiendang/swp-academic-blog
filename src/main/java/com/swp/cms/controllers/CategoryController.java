package com.swp.cms.controllers;

import com.swp.cms.dto.CategoryDto;
import com.swp.cms.mapper.CategoryMapper;
import com.swp.cms.reqDto.CategoryRequest;
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
        List<CategoryDto> categoryDtos = cate.stream()
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());
        //return makeResponse(true, testingDto, "Get testing detail successful!");
        return categoryDtos;
    }

    @GetMapping("/{id}")
    public CategoryDto getCateById(@PathVariable Integer id) {

        Category category = categoryService.getById(id);
        CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);
        return categoryDto;
    }
    @PostMapping("/post")
    public CategoryDto addCategory(@RequestBody CategoryRequest categoryRequest) {
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
