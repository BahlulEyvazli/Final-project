package com.example.bazaarstore.controller;

import com.example.bazaarstore.dto.ProductDTO;
import com.example.bazaarstore.model.entity.Category;
import com.example.bazaarstore.model.entity.Product;
import com.example.bazaarstore.repository.CategoryRepository;
import com.example.bazaarstore.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bazaar/product")
public class ProductController {

    private ProductService productService;

    private CategoryRepository categoryRepository;

    public ProductController(ProductService productService, CategoryRepository categoryRepository) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO productDTO){
        Category category = categoryRepository.findById(productDTO.getCategoryId()).orElseThrow();
        productService.createProduct(productDTO,category);
        return ResponseEntity.ok("Product created");
    }


    @GetMapping("/findAll")
    public List<Product> findAll(){

        return productService.productList();
    }

    @GetMapping("/find/{id}")
    public Product findProduct(@PathVariable Long id){
        return productService.findProduct(id);
    }


    @PostMapping("/{id}/update")
    private ResponseEntity<?> updateProduct(@PathVariable("id") Long id,@RequestBody ProductDTO productDTO){
        Product product = productService.updateProduct(id,productDTO);
        return ResponseEntity.ok(product);
    }

}
