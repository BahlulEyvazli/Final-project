package com.example.bazaarstore.service;

import com.example.bazaarstore.dto.cart.AddToCartDTO;
import com.example.bazaarstore.dto.cart.CartDTO;
import com.example.bazaarstore.dto.cart.CartItemDTO;
import com.example.bazaarstore.model.entity.Cart;
import com.example.bazaarstore.model.entity.Product;
import com.example.bazaarstore.model.entity.User;
import com.example.bazaarstore.repository.CartRepository;
import com.example.bazaarstore.repository.ProductRepository;
import com.example.bazaarstore.repository.UserRepository;
import lombok.SneakyThrows;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, JwtService jwtService, UserRepository userRepository,
                       ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }


    public Cart addToCart(AddToCartDTO addToCartDTO){
        Product product = productRepository.findById(addToCartDTO.getProductId()).orElseThrow();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        Cart cart = Cart.builder().product(product)
                .user(user).quantity(addToCartDTO.getQuantity())
                .createdDate(new Date()).build();
        return cartRepository.save(cart);
    }

    public CartDTO listCartItems(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();

       List<Cart> cartList =  cartRepository.findAllByUserOrderByCreatedDateDesc(user);
       List<CartItemDTO> cartItemDTOS = new ArrayList<>();
       double totalCost = 0;
       for(Cart cart : cartList){
           CartItemDTO cartItemDTO = new CartItemDTO(cart);
           totalCost += cartItemDTO.getProduct().getUnitPrice()*cartItemDTO.getQuantity();
           cartItemDTOS.add(cartItemDTO);
       }

       return CartDTO.builder().cartItems(cartItemDTOS).totalCost(totalCost).build();
    }

    @SneakyThrows
    public void deleteCartItem(Long cartItemId){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        Cart cart = cartRepository.findById(cartItemId).orElseThrow();

        if (cart.getUser()!=user){
            throw new Exception("Cart is not belong to user :" +cartItemId);
        }

        cartRepository.delete(cart);
    }

    public void deleteUserCartItems(User user) {
        cartRepository.deleteByUser(user);
    }

}
