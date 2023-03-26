package com.example.bazaarstore.service;

import com.example.bazaarstore.dto.cart.CartDTO;
import com.example.bazaarstore.dto.cart.CartItemDTO;
import com.example.bazaarstore.dto.payment.PaymentDTO;
import com.example.bazaarstore.dto.product.ProductShowDTO;
import com.example.bazaarstore.model.entity.*;
import com.example.bazaarstore.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CartService {
    private final CategoryRepository categoryRepository;
    private final CartItemRepository cartItemRepository;
    private final TokenRepository tokenRepository;

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository,
                       UserRepository userRepository,
                       ProductRepository productRepository,
                       TokenRepository tokenRepository,
                       CartItemRepository cartItemRepository,
                       CategoryRepository categoryRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.tokenRepository = tokenRepository;
        this.cartItemRepository = cartItemRepository;
        this.categoryRepository = categoryRepository;
    }

    public ProductShowDTO addProductToCart(Long productId,int quantity){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();
        log.info("Product id :" + product.getId());
        if (cartRepository.findByUser(user).isEmpty()){
            Cart cart = Cart.builder().user(user).build();
            cartRepository.save(cart);
        }
        Cart cart = cartRepository.findByUser(user).orElseThrow();
        cartItemRepository.save(CartItem.builder().cart(cart).quantity(quantity).product(product).build());

        return ProductShowDTO.builder().productId(product.getId()).name(product.getName()).sku(product.getSku())
                .categoryName(product.getCategory().getCategoryName()).unitPrice(product.getUnitPrice())
                .image(product.getImage())
                .description(product.getDescription()).unitsInStock(product.getUnitsInStock())
                .username(product.getUser().getUsername()).build();
    }

    public CartDTO showCart(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();

        Cart cart = cartRepository.findByUser(user).orElseThrow();
        log.info("Cart :" + cart.getUser().getUsername());
        List<CartItem> cartItems = cartItemRepository.findAllByCart(cart);
        log.info("cart item :" + cartItems.get(0).getProduct().getName());
        List<CartItemDTO> cartItemDTOS = cartItems.stream().map(cartItem -> CartItemDTO.builder()
                .product(cartItem.getProduct()).quantity(cartItem.getQuantity())
                .cost(cartItem.getProduct().getUnitPrice()* cartItem.getQuantity()).build()).toList();

        double totalCost = 0;
        for (CartItemDTO c : cartItemDTOS){
            totalCost+=c.getCost();
        }
        log.info("total cost :" + totalCost);
        return CartDTO.builder().cartItemDTOS(cartItemDTOS).totalCost(totalCost).build();
    }


    public String makePayment(PaymentDTO paymentDTO){
        CartDTO cartDTO =showCart();
        return "succesfull , Total cost :" + cartDTO.getTotalCost();
    }


}
