package com.example.bazaarstore.service;

import com.example.bazaarstore.dto.category.CategoryDTO;
import com.example.bazaarstore.dto.product.ProductShowDTO;
import com.example.bazaarstore.model.entity.Category;
import com.example.bazaarstore.model.entity.Product;
import com.example.bazaarstore.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void createCategory(Category category){
        categoryRepository.save(category);
    }


    public CategoryDTO findById(Long id){
        Category category =  categoryRepository.findById(id).orElseThrow();
        List<Product> products = category.getProducts().stream().toList();
        List<ProductShowDTO> productDTOS =
                products.stream().map(product -> ProductShowDTO.builder().productId(product.getId())
                        .name(product.getName()).sku(product.getSku()).categoryName(product.getCategory().getCategoryName())
                        .unitPrice(product.getUnitPrice())
                        .image(product.getImage())
                        .unitsInStock(product.getUnitsInStock())
                        .description(product.getDescription()).username(product.getUser().getUsername())
                        .build()).toList();


        return CategoryDTO
                .builder().categoryName(category.getCategoryName()).productDTOS(productDTOS).build();
    }

    public Category updateCategory(Category category){
        return categoryRepository.save(category);
    }


}
