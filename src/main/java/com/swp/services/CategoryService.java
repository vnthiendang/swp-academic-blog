package com.swp.services;

import com.swp.entities.Category;
import com.swp.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
