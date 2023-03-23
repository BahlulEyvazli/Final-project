package com.example.bazaarstore.controller;

import com.example.bazaarstore.dto.product.ProductDTO;
import com.example.bazaarstore.model.entity.Category;
import com.example.bazaarstore.model.entity.Product;
import com.example.bazaarstore.repository.CategoryRepository;
import com.example.bazaarstore.service.ProductService;
import com.example.bazaarstore.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bazaar/products")
public class ProductController {

    private final ProductService productService;

    private final CategoryRepository categoryRepository;

    private final UserService userService;


    public ProductController(ProductService productService, CategoryRepository categoryRepository, UserService userService) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO productDTO){
        Category category = categoryRepository.findCategoryByCategoryName(productDTO.getCategoryName()).orElse(null);
        productService.createProduct(productDTO,category);
        return ResponseEntity.ok("Product created");
    }


    @GetMapping("/findAll")
    public List<ProductDTO> findAll(){

        return productService.productList();
    }

    @GetMapping("/find/{id}")
    public ProductDTO findProduct(@PathVariable("id") Long id){
        return productService.findProduct(id);
    }

    //must be secure
    @PostMapping("/{id}/update")
    private ResponseEntity<?> updateProduct(@PathVariable("id") Long id,@RequestBody ProductDTO productDTO){
        Product product = productService.updateProduct(id,productDTO);
        return ResponseEntity.ok(product);
    }


}
