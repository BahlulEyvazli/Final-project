package com.example.bazaarstore.controller;

import com.example.bazaarstore.model.entity.Product;
import com.example.bazaarstore.model.entity.WishList;
import com.example.bazaarstore.service.WishListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//must be secure
@RestController
@RequestMapping("/bazaar/wishlist")
public class WishListController {
    private final WishListService wishListService;

    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    @GetMapping("/{token}")
    public ResponseEntity<?> getAll(@PathVariable("token") String token){
        List<WishList> list = wishListService.showWishList(token);
        return ResponseEntity.ok(list);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addToList(@RequestParam("token") String token, @RequestBody Product product){
        WishList wishList = wishListService.addwishList(token,product);
        return ResponseEntity.ok(wishList);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteFromWishList(@RequestParam("productId") Long productId,@RequestParam("token") String token){
        wishListService.deleteWishList(token,productId);
        return ResponseEntity.ok("Product deleted");
    }
}
