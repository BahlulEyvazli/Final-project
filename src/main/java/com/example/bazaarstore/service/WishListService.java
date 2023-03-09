package com.example.bazaarstore.service;


import com.example.bazaarstore.model.entity.Product;
import com.example.bazaarstore.model.entity.User;
import com.example.bazaarstore.model.entity.WishList;
import com.example.bazaarstore.repository.ProductRepository;
import com.example.bazaarstore.repository.UserRepository;
import com.example.bazaarstore.repository.WishListRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class WishListService {

    private final WishListRepository wishListRepository;

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final JwtService jwtService;

    public WishListService(WishListRepository wishListRepository, UserRepository userRepository,
                           ProductRepository productRepository, JwtService jwtService) {
        this.wishListRepository = wishListRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.jwtService = jwtService;
    }

    public WishList addwishList(String token, Product product){
        User user = userRepository.findByUsername(jwtService.extractUserName(token)).orElseThrow();
        return wishListRepository.save(WishList.builder().user(user).product(product).build());
    }

    public List<WishList> showWishList(String token){
        String username = jwtService.extractUserName(token);
        log.error("USERNAME IS" + username);
        User user = userRepository.findByUsername(username).orElseThrow();
        return wishListRepository.findAllByUser(user);
    }

    public void deleteWishList(String token,Long productId){
        User user = userRepository.findByUsername(jwtService.extractUserName(token)).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();
        wishListRepository.delete(WishList.builder().user(user).product(product).build());
    }

}
