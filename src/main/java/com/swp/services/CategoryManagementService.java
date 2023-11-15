package com.swp.services;

import com.swp.cms.reqDto.CategoryManagementRequest;
import com.swp.entities.CategoryManagement;
import com.swp.repositories.CategoryManagementRepository;
import com.swp.repositories.CategoryRepository;
import com.swp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryManagementService {
    @Autowired
    private CategoryManagementRepository categoryManagementRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryManagement getById(Integer id) {
        return categoryManagementRepository.findById(id).orElseThrow();
    }

    public Boolean existsById(Integer id) {
        return categoryManagementRepository.existsById(id);
    }

    public void deleteById(Integer id) {
        categoryManagementRepository.deleteById(id);
    }

    public CategoryManagement add(CategoryManagement cate) {
        return categoryManagementRepository.save(cate);
    }

    public List<CategoryManagement> getAll() {
        return categoryManagementRepository.findAll();
    }

    public CategoryManagement createCategoryManagement(CategoryManagementRequest categoryManagementRequest){
        CategoryManagement categoryManagement = new CategoryManagement();
        categoryManagement.setCreatedTime(LocalDateTime.now());
        categoryManagement.setTeacher(userRepository.findById(categoryManagementRequest.getTeacherId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Teacher")));
        categoryManagement.setCategory(categoryRepository.findByContent(categoryManagementRequest.getCategoryName())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Category")));
        return categoryManagementRepository.save(categoryManagement);
    }
}
