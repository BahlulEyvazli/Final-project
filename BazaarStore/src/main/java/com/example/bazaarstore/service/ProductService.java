package com.example.bazaarstore.service;

import com.example.bazaarstore.dto.product.ProductDTO;
import com.example.bazaarstore.dto.product.ProductShowDTO;
import com.example.bazaarstore.model.entity.Category;
import com.example.bazaarstore.model.entity.Product;
import com.example.bazaarstore.model.entity.User;
import com.example.bazaarstore.repository.CategoryRepository;
import com.example.bazaarstore.repository.ProductRepository;
import com.example.bazaarstore.repository.UserRepository;
import com.itextpdf.text.DocumentException;
import jakarta.mail.MessagingException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.List;


@Service
public class ProductService {
    private final ProductRepository productRepository;

    private final MailService mailService;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, MailService mailService,
                          UserRepository userRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.mailService = mailService;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public void createProduct(ProductDTO productDTO) {
        Category category = categoryRepository.findCategoryByCategoryName(productDTO.getCategoryName()).orElse(null);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();

        Product product = Product.builder()
                .name(productDTO.getName())
                .active(true)
                .sku(productDTO.getSku())
                .description(productDTO.getDescription())
                .unitPrice(productDTO.getUnitPrice())
                .unitsInStock(productDTO.getUnitsInStock())
                .category(category)
                .user(user).build();

        productRepository.save(product);
        String text = "PRODUCT CREATED : " +"\n" + productDTO;
        mailService.sendSimpleMail(user.getEmail(), product.getName(),text);
        try {
            mailService.sendEmailWithAttachment(user.getEmail(),product.getName(),text,productDTO);
        } catch (MessagingException | DocumentException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    public List<ProductShowDTO> productList(){
        List<Product> products = productRepository.findAllByActiveIsTrue();
        return products.stream().map(product -> ProductShowDTO.builder().productId(product.getId())
                .name(product.getName()).sku(product.getSku()).categoryName(product.getCategory().getCategoryName())
                .unitPrice(product.getUnitPrice())
                .image(product.getImage())
                .unitsInStock(product.getUnitsInStock())
                .description(product.getDescription()).username(product.getUser().getUsername())
                .build()).toList();
    }

    public ProductShowDTO findProduct(Long id){
        Product product = productRepository.findById(id).orElseThrow();
        return ProductShowDTO.builder().productId(product.getId()).name(product.getName()).sku(product.getSku())
                .categoryName(product.getCategory().getCategoryName()).unitPrice(product.getUnitPrice())
                .image(product.getImage())
                .description(product.getDescription()).unitsInStock(product.getUnitsInStock())
                .username(product.getUser().getUsername()).build();
    }

    public Product updateProduct(Long id,ProductDTO productDTO) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        Product finded = productRepository.findById(id).orElseThrow();
        if (finded.getUser().equals(user)) {
            finded.setName(productDTO.getName());
            finded.setDescription(productDTO.getDescription());
            finded.setSku(productDTO.getSku());
            finded.setUnitPrice(productDTO.getUnitPrice());
            finded.setUnitsInStock(productDTO.getUnitsInStock());
            return productRepository.save(finded);
        }
        else {
            return null;
        }
    }

    public void deleteMyProduct(Long productId){
        Product product = productRepository.findById(productId).orElseThrow();
        product.setActive(false);
        productRepository.save(product);
    }



}