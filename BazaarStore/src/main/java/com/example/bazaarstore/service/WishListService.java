package com.example.bazaarstore.service;


import com.example.bazaarstore.dto.product.ProductShowDTO;
import com.example.bazaarstore.model.entity.Product;
import com.example.bazaarstore.model.entity.User;
import com.example.bazaarstore.model.entity.WishList;
import com.example.bazaarstore.repository.ProductRepository;
import com.example.bazaarstore.repository.UserRepository;
import com.example.bazaarstore.repository.WishListRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class WishListService {

    private final WishListRepository wishListRepository;

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    public WishListService(WishListRepository wishListRepository, UserRepository userRepository,
                           ProductRepository productRepository) {
        this.wishListRepository = wishListRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public ProductShowDTO addwishList(Long productId){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();
        log.info("Product id :" + product.getId());
        wishListRepository.save(WishList.builder().user(user).product(product).build());
        return ProductShowDTO.builder().productId(product.getId()).name(product.getName()).sku(product.getSku())
                .categoryName(product.getCategory().getCategoryName()).unitPrice(product.getUnitPrice())
                .image(product.getImage())
                .description(product.getDescription()).unitsInStock(product.getUnitsInStock())
                .username(product.getUser().getUsername()).build();
    }

    public List<ProductShowDTO> showWishList(String username){
        User user = userRepository.findByUsername(username).orElseThrow();
        List<Product> products = wishListRepository.findActiveProductsByUser(user);
        return products.stream().map(product -> ProductShowDTO.builder().productId(product.getId())
                .name(product.getName()).sku(product.getSku()).categoryName(product.getCategory().getCategoryName())
                .unitPrice(product.getUnitPrice())
                .image(product.getImage())
                .unitsInStock(product.getUnitsInStock())
                .description(product.getDescription()).username(product.getUser().getUsername())
                .build()).toList();
    }

    public void deleteWishList(Long productId){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();
        wishListRepository.delete(WishList.builder().user(user).product(product).build());
    }

    public ProductShowDTO getProductFromWishList(Long productId){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        if (wishListRepository.findWishListByProductIdAndUserId(productId,user.getId()).isPresent()){

            Product product = productRepository.findById(productId).orElseThrow();
            return ProductShowDTO.builder().productId(product.getId()).name(product.getName()).sku(product.getSku())
                    .categoryName(product.getCategory().getCategoryName()).unitPrice(product.getUnitPrice())
                    .image(product.getImage())
                    .description(product.getDescription()).unitsInStock(product.getUnitsInStock())
                    .username(product.getUser().getUsername()).build();
        }
        else {
            return null;
        }
    }

}
