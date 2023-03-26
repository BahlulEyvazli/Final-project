package com.example.bazaarstore.controller;

import com.example.bazaarstore.dto.user.UserProfileDTO;
import com.example.bazaarstore.service.ImageService;
import com.example.bazaarstore.service.ProductService;
import com.example.bazaarstore.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Slf4j
@RestController
@RequestMapping("/bazaar")
public class UserController {

    private final UserService userService;
    private final ProductService productService;

    private final ImageService imageService;

    public UserController(UserService userService, ProductService productService, ImageService imageService) {
        this.userService = userService;
        this.productService = productService;
        this.imageService = imageService;
    }


    @GetMapping("{username}/profile")
    public ResponseEntity<?> getProfile(@PathVariable("username") String username) {
        UserProfileDTO user = userService.findUserByUsername(username);
        return ResponseEntity.ok(user);
    }


    @GetMapping("/myprofile")
    public ResponseEntity<?> getMyProducts(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserProfileDTO user = userService.findUserByUsername(userDetails.getUsername());

        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/myprofile/{productId}")
    public ResponseEntity<?> deleteMyProduct(@PathVariable("productId") Long productId){
        productService.deleteMyProduct(productId);
        return ResponseEntity.ok("Product deleted");
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<?> uploadImage(@PathVariable("userId") Long userId
            ,@RequestParam("image") MultipartFile file) throws IOException {
        byte[] image = imageService.uploadImage(file,userId);
        return ResponseEntity.ok(image);
    }


}
