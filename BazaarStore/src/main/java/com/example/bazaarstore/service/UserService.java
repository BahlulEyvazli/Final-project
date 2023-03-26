package com.example.bazaarstore.service;

import com.example.bazaarstore.dto.product.ProductShowDTO;
import com.example.bazaarstore.dto.user.UserProfileDTO;
import com.example.bazaarstore.model.entity.Product;
import com.example.bazaarstore.model.entity.User;
import com.example.bazaarstore.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserProfileDTO findUserByUsername(String username){
        User user = userRepository.findByUsername(username).orElseThrow();
        List<Product> products = user.getProducts().stream().toList();
        List<ProductShowDTO> dtoList = products.stream().map(product -> ProductShowDTO.builder().productId(product.getId())
                .name(product.getName()).sku(product.getSku()).categoryName(product.getCategory().getCategoryName())
                .unitPrice(product.getUnitPrice())
                .image(product.getImage())
                .unitsInStock(product.getUnitsInStock())
                .description(product.getDescription()).username(product.getUser().getUsername())
                .build()).toList();

        return UserProfileDTO.builder().list(dtoList).profile(user.getImage())
                .email(user.getEmail()).username(user.getUsername()).phoneNumber(user.getPhoneNumber()).build();
    }


}
