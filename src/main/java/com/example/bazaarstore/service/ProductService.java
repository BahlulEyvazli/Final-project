package com.example.bazaarstore.service;

import com.example.bazaarstore.dto.ProductDTO;
import com.example.bazaarstore.model.entity.Category;
import com.example.bazaarstore.model.entity.Product;
import com.example.bazaarstore.model.entity.User;
import com.example.bazaarstore.repository.ProductRepository;
import com.example.bazaarstore.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class ProductService {
    private final ProductRepository productRepository;

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public ProductService(ProductRepository productRepository, JwtService jwtService,
                          UserRepository userRepository) {
        this.productRepository = productRepository;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    public void createProduct(ProductDTO productDTO, Category category, String token ){
        User user = userRepository.findByUsername(jwtService.extractUserName(token)).orElseThrow();
        Product product = Product.builder()
                .name(productDTO.getName())
                .dateCreated(productDTO.getDateCreated())
                .active(productDTO.isActive())
                .sku(productDTO.getSku())
                .description(productDTO.getDescription())
                .imageUrl(productDTO.getImageUrl())
                .lastUpdated(productDTO.getLastUpdated())
                .unitPrice(productDTO.getUnitPrice())
                .unitsInStock(productDTO.getUnitsInStock())
                .category(category)
                .user(user).build();
        productRepository.save(product);
    }


    public List<Product> productList(){
        return productRepository.findAll();
    }

    public Product findProduct(Long id){
        Optional<Product> productOptional = productRepository.findById(id);
        return productOptional.orElse(Product.builder().build());
    }

    public Product updateProduct(Long id,ProductDTO productDTO){
        Product finded = productRepository.findById(id).orElseThrow();
        finded.setActive(productDTO.isActive());
        finded.setName(productDTO.getName());
        finded.setDescription(productDTO.getDescription());
        finded.setLastUpdated(productDTO.getLastUpdated());
        finded.setSku(productDTO.getSku());
        finded.setImageUrl(productDTO.getImageUrl());
        finded.setDateCreated(productDTO.getDateCreated());
        finded.setUnitPrice(productDTO.getUnitPrice());
        finded.setUnitsInStock(productDTO.getUnitsInStock());
        return productRepository.save(finded);
    }




}