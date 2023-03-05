package com.example.bazaarstore.service;

import com.example.bazaarstore.model.entity.Category;
import com.example.bazaarstore.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void createCategory(Category category){
        categoryRepository.save(category);
    }


    public List<Category> categoryListtList(){
        return categoryRepository.findAll();
    }

    public Category findById(Long id){
        return categoryRepository.findById(id).orElseThrow();
    }

    public Category updateCategory(Category category){
        return categoryRepository.save(category);
    }


}
