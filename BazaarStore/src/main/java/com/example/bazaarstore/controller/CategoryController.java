package com.example.bazaarstore.controller;

import com.example.bazaarstore.dto.category.CategoryDTO;
import com.example.bazaarstore.model.entity.Category;
import com.example.bazaarstore.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bazaar/category")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("find/{id}")
    public ResponseEntity<?> findCategory(@PathVariable("id") Long id){
        CategoryDTO category= categoryService.findById(id);
        return ResponseEntity.ok(category);
    }

    //must be secure
    @PostMapping("/create")
    public ResponseEntity<?> createCategory(@RequestBody Category category){
        categoryService.createCategory(category);
       return ResponseEntity.ok("successfull");
    }

    //must be secure
    @PostMapping("/update")
    public ResponseEntity<?> updateCategory(@RequestBody Category category){
        Category result = categoryService.updateCategory(category);
        return ResponseEntity.ok(result);
    }


}
