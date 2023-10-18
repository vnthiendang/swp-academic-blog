package com.swp.services;

import com.swp.cms.reqDto.CategoryRequest;
import com.swp.entities.Category;
import com.swp.entities.Category;
import com.swp.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Category getById(Integer id) {
        return categoryRepository.findById(id).orElseThrow();
    }

    public Boolean existsById(Integer id) {
        return categoryRepository.existsById(id);
    }

    public void deleteById(Integer id) {
        categoryRepository.deleteById(id);
    }

    public Category add(Category cate) {
        return categoryRepository.save(cate);
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Category createCategory(CategoryRequest categoryRequest){
        Category category = new Category();
        category.setCreatedDate(LocalDateTime.now());
        category.setContent(categoryRequest.getContent());
        if(categoryRequest.getParentCategory() != null) {
            category.setParentCategory(categoryRepository.findById(categoryRequest.getParentCategory()).
                    orElseThrow(() -> new IllegalArgumentException("Invalid Category")));
        } else {
            category.setParentCategory(null);
        }
        return categoryRepository.save(category);
    }
    public Category updateCategory(Integer categoryID, CategoryRequest categoryRequest){
        Category category = getById(categoryID);
        category.setContent(categoryRequest.getContent());
        if(categoryRequest.getParentCategory() != null) {
            category.setParentCategory(categoryRepository.findById(categoryRequest.getParentCategory()).
                    orElseThrow(() -> new IllegalArgumentException("Invalid Category")));
        } else {
            category.setParentCategory(null);
        }
        category.setCreatedDate(LocalDateTime.now());
        return categoryRepository.save(category); // Save and return the updated post
    }
}
