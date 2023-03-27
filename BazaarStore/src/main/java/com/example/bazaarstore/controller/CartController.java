package com.example.bazaarstore.controller;

import com.example.bazaarstore.dto.cart.CartDTO;
import com.example.bazaarstore.dto.payment.PaymentDTO;
import com.example.bazaarstore.dto.product.ProductShowDTO;
import com.example.bazaarstore.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bazaar")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/products/{productId}/{quantity}")
    public ResponseEntity<?> addProductToCart(@PathVariable("productId") Long productId, @PathVariable("quantity") int quantity){
       ProductShowDTO productShowDTO = cartService.addProductToCart(productId,quantity);
       return ResponseEntity.ok(productShowDTO);
    }

    @GetMapping("/cart")
    public ResponseEntity<?> showCart(){
        var cartDTO = cartService.showCart();
        return ResponseEntity.ok(cartDTO);
    }


    @PostMapping("/payment")
    public ResponseEntity<?> makePayment(@RequestBody PaymentDTO paymentDTO){
        var result = cartService.makePayment(paymentDTO);
        return ResponseEntity.ok(result);
    }

}
