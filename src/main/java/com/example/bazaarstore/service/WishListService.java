package com.example.bazaarstore.service;


import com.example.bazaarstore.dto.product.ProductDTO;
import com.example.bazaarstore.dto.wishlist.WishListDTO;
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
import java.util.stream.Collectors;

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

    public ProductDTO addwishList(Long productId){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();
        log.info("Product id :" + product.getId());
        wishListRepository.save(WishList.builder().user(user).product(product).build());
        return ProductDTO.builder().productId(product.getId()).name(product.getName()).sku(product.getSku())
                .categoryName(product.getCategory().getCategoryName()).unitPrice(product.getUnitPrice())
                .imageUrl(product.getImageUrl()).description(product.getDescription()).unitsInStock(product.getUnitsInStock())
                .username(product.getUser().getUsername()).build();
    }

    public List<ProductDTO> showWishList(String username){
        User user = userRepository.findByUsername(username).orElseThrow();
        List<WishList> wishLists = wishListRepository.findAllByUser(user);
        return wishLists.stream().map(wishList ->
                ProductDTO.builder().productId(wishList.getProduct().getId()).sku(wishList.getProduct().getSku())
                        .categoryName(wishList.getProduct().getCategory().getCategoryName())
                        .name(wishList.getProduct().getName()).unitPrice(wishList.getProduct().getUnitPrice())
                        .username(wishList.getProduct().getUser().getUsername())
                        .imageUrl(wishList.getProduct().getImageUrl()).description(wishList.getProduct().getDescription())
                        .unitsInStock(wishList.getProduct().getUnitsInStock()).build()
                ).toList();
    }

    public void deleteWishList(Long productId){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();
        wishListRepository.delete(WishList.builder().user(user).product(product).build());
    }

    public ProductDTO getProductFromWishList(Long productId){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        if (wishListRepository.findWishListByProductIdAndUserId(productId,user.getId()).isPresent()){

            Product product = productRepository.findById(productId).orElseThrow();
            return ProductDTO.builder().productId(product.getId()).name(product.getName()).sku(product.getSku())
                    .categoryName(product.getCategory().getCategoryName()).unitPrice(product.getUnitPrice())
                    .imageUrl(product.getImageUrl()).description(product.getDescription()).unitsInStock(product.getUnitsInStock())
                    .username(product.getUser().getUsername()).build();
        }
        else {
            return null;
        }
    }

}
