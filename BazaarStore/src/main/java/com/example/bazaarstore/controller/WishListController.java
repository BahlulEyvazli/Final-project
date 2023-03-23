package com.example.bazaarstore.controller;

import com.example.bazaarstore.dto.product.ProductDTO;
import com.example.bazaarstore.model.entity.WishList;
import com.example.bazaarstore.service.WishListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bazaar")
public class WishListController {
    private final WishListService wishListService;

    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    @GetMapping("wishlist/{username}")
    public ResponseEntity<?> getAll(@PathVariable("username") String username){
        List<ProductDTO> list = wishListService.showWishList(username);
        return ResponseEntity.ok(list);
    }

    @PostMapping("/products/{productId}/addwishlist")
    public ResponseEntity<?> addToList(@PathVariable("productId") Long productId){
        ProductDTO wishList = wishListService.addwishList(productId);
        return ResponseEntity.ok(wishList);
    }

    @GetMapping("/user/wishlist/mywishlist/{productId}")
    public ResponseEntity<?> getProductFromWishList(@PathVariable("productId") Long productId){
        ProductDTO productDTO = wishListService.getProductFromWishList(productId);
        return ResponseEntity.ok(productDTO);
    }


    @DeleteMapping("/user/wishlist/mywishlist/{productId}/delete")
    public ResponseEntity<?> deleteFromWishList(@PathVariable("productId") Long productId){
        wishListService.deleteWishList(productId);
        return ResponseEntity.ok("Product deleted");
    }
}
