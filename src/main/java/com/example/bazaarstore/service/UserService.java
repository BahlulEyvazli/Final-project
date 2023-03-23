package com.example.bazaarstore.service;


import com.example.bazaarstore.dto.product.ProductDTO;
import com.example.bazaarstore.dto.user.UserDTO;
import com.example.bazaarstore.dto.user.UserProfileDTO;
import com.example.bazaarstore.model.entity.Product;
import com.example.bazaarstore.model.entity.User;
import com.example.bazaarstore.repository.ProductRepository;
import com.example.bazaarstore.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    public UserService(UserRepository userRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public UserProfileDTO findUserByUsername(String username){
        User user = userRepository.findByUsername(username).orElseThrow();
        List<Product> products = user.getProducts().stream().toList();
        List<ProductDTO> dtoList = products.stream().map(product -> ProductDTO.builder().productId(product.getId())
                .name(product.getName()).sku(product.getSku()).categoryName(product.getCategory().getCategoryName())
                .unitPrice(product.getUnitPrice()).imageUrl(product.getImageUrl()).unitsInStock(product.getUnitsInStock())
                .description(product.getDescription()).username(product.getUser().getUsername())
                .build()).toList();
        return UserProfileDTO.builder().list(dtoList)
                .email(user.getEmail()).username(user.getUsername()).phoneNumber(user.getPhoneNumber()).build();
    }


}
