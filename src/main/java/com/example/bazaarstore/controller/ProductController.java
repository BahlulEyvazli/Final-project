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

    private final ProductService productService;

    private final CategoryRepository categoryRepository;


    public ProductController(ProductService productService, CategoryRepository categoryRepository) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
    }

    //must be secure
    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestParam("token") String token,@RequestBody ProductDTO productDTO){
        Category category = categoryRepository.findById(productDTO.getCategoryId()).orElseThrow();
        productService.createProduct(productDTO,category,token);
        return ResponseEntity.ok("Product created");
    }


    @GetMapping("/findAll")
    public List<Product> findAll(){

        return productService.productList();
    }

    @GetMapping("/find/{id}")
    public Product findProduct(@PathVariable("id") Long id){
        return productService.findProduct(id);
    }

    //must be secure
    @PostMapping("/{id}/update")
    private ResponseEntity<?> updateProduct(@PathVariable("id") Long id,@RequestBody ProductDTO productDTO){
        Product product = productService.updateProduct(id,productDTO);
        return ResponseEntity.ok(product);
    }

    //TODO DeleteMapping

}
