package com.example.bazaarstore.controller;

import com.example.bazaarstore.dto.cart.AddToCartDTO;
import com.example.bazaarstore.dto.cart.CartDTO;
import com.example.bazaarstore.model.entity.Cart;
import com.example.bazaarstore.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bazaar/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestParam("token") String token, @RequestBody AddToCartDTO addToCartDTO){
        Cart cart= cartService.addToCart(addToCartDTO,token);
        return ResponseEntity.ok(cart);
    }

    @GetMapping("")
    public ResponseEntity<?> getCartItems(@RequestParam("token") String token){
        CartDTO cartDTO = cartService.listCartItems(token);
        return ResponseEntity.ok(cartDTO);
    }

    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<?> deleteCartItem(@PathVariable("cartItemId") Long cartItemId
            ,@RequestParam("token") String token){
        cartService.deleteCartItem(cartItemId,token);
        return ResponseEntity.ok("Cart deleted");
    }
}
