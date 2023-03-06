package com.example.bazaarstore.controller;

import com.example.bazaarstore.dto.ProductDTO;
import com.example.bazaarstore.model.entity.Category;
import com.example.bazaarstore.model.entity.Product;
import com.example.bazaarstore.model.entity.User;
import com.example.bazaarstore.repository.CategoryRepository;
import com.example.bazaarstore.service.ProductService;
import com.example.bazaarstore.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bazaar/product")
public class ProductController {

    private ProductService productService;
    private UserService userService;

    private CategoryRepository categoryRepository;

    public ProductController(ProductService productService, CategoryRepository categoryRepository,UserService userService) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
        this.userService=userService;
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity<?> createProduct(
            @PathVariable("userId") long id,
            @RequestBody ProductDTO productDTO){
        User user = userService.getUserById(id);
        productDTO.setUser(user);
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
