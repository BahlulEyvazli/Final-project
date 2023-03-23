package com.example.bazaarstore.controller;

import com.example.bazaarstore.dto.cart.AddToCartDTO;
import com.example.bazaarstore.dto.cart.CartDTO;
import com.example.bazaarstore.model.entity.Cart;
import com.example.bazaarstore.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody AddToCartDTO addToCartDTO){
        Cart cart= cartService.addToCart(addToCartDTO);
        return ResponseEntity.ok(cart);
    }

    @GetMapping("")
    public ResponseEntity<?> getCartItems(){
        CartDTO cartDTO = cartService.listCartItems();
        return ResponseEntity.ok(cartDTO);
    }

    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<?> deleteCartItem(@PathVariable("cartItemId") Long cartItemId){
        cartService.deleteCartItem(cartItemId);
        return ResponseEntity.ok("Cart deleted");
    }
}
