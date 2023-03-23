package com.example.bazaarstore.service;

import com.example.bazaarstore.dto.product.ProductDTO;
import com.example.bazaarstore.model.entity.Category;
import com.example.bazaarstore.model.entity.Product;
import com.example.bazaarstore.model.entity.User;
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

    public ProductService(ProductRepository productRepository, MailService mailService,UserRepository userRepository) {
        this.productRepository = productRepository;
        this.mailService = mailService;
        this.userRepository = userRepository;
    }

    public void createProduct(ProductDTO productDTO, Category category){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        Product product = Product.builder()
                .name(productDTO.getName())
                .active(true)
                .sku(productDTO.getSku())
                .description(productDTO.getDescription())
                .imageUrl(productDTO.getImageUrl())
                .unitPrice(productDTO.getUnitPrice())
                .unitsInStock(productDTO.getUnitsInStock())
                .category(category)
                .user(user).build();
        productRepository.save(product);
        String text = "PRODUCT CREATED : " +"\n" + productDTO.toString();
        mailService.sendSimpleMail(user.getEmail(), product.getName(),text);
        try {
            mailService.sendEmailWithAttachment(user.getEmail(),product.getName(),text,productDTO);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public List<ProductDTO> productList(){
        List<Product> products = productRepository.findAllByActiveIsTrue();
        return products.stream().map(product -> ProductDTO.builder().productId(product.getId())
                .name(product.getName()).sku(product.getSku()).categoryName(product.getCategory().getCategoryName())
                .unitPrice(product.getUnitPrice()).imageUrl(product.getImageUrl()).unitsInStock(product.getUnitsInStock())
                .description(product.getDescription()).username(product.getUser().getUsername())
                .build()).toList();
    }

    public ProductDTO findProduct(Long id){
        Product product = productRepository.findById(id).orElseThrow();
        return ProductDTO.builder().productId(product.getId()).name(product.getName()).sku(product.getSku())
                .categoryName(product.getCategory().getCategoryName()).unitPrice(product.getUnitPrice())
                .imageUrl(product.getImageUrl()).description(product.getDescription()).unitsInStock(product.getUnitsInStock())
                .username(product.getUser().getUsername()).build();
    }

    public Product updateProduct(Long id,ProductDTO productDTO){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        Product finded = productRepository.findById(id).orElseThrow();
        if (finded.getUser().equals(user)) {
            finded.setName(productDTO.getName());
            finded.setDescription(productDTO.getDescription());
            finded.setSku(productDTO.getSku());
            finded.setImageUrl(productDTO.getImageUrl());
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