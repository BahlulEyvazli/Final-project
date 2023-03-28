package com.example.bazaarstore.controller;

import com.example.bazaarstore.dto.product.ProductDTO;
import com.example.bazaarstore.dto.product.ProductShowDTO;
import com.example.bazaarstore.model.entity.Product;
import com.example.bazaarstore.repository.CategoryRepository;
import com.example.bazaarstore.service.ImageService;
import com.example.bazaarstore.service.ProductService;
import com.example.bazaarstore.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/bazaar/products")
public class ProductController {

    private final ProductService productService;

    private final CategoryRepository categoryRepository;

    private final UserService userService;

    private final ImageService imageService;


    public ProductController(ProductService productService, CategoryRepository categoryRepository
            ,UserService userService, ImageService imageService) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
        this.userService = userService;
        this.imageService = imageService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO productDTO) throws IOException {
        productService.createProduct(productDTO);
        return ResponseEntity.ok("Product created");
    }


    @GetMapping("/findAll")
    public List<ProductShowDTO> findAll(){

        return productService.productList();
    }

    @GetMapping("/find/{id}")
    public ProductShowDTO findProduct(@PathVariable("id") Long id){
        return productService.findProduct(id);
    }

    //must be secure
    @PostMapping("/{id}/update")
    private ResponseEntity<?> updateProduct(@PathVariable("id") Long id,@RequestBody ProductDTO productDTO) {
        productService.updateProduct(id,productDTO);
        return ResponseEntity.ok("Product updated");
    }

    @PostMapping("/product/{productId}")
    public ResponseEntity<?> uploadImage(@PathVariable("productId") Long productId
            ,@RequestParam("image") MultipartFile file) throws IOException {
        byte[] image = imageService.uploadImage(file,productId);
        return ResponseEntity.ok(image);
    }


}
