package com.example.bazaarstore.controller;

import com.example.bazaarstore.dto.product.ProductShowDTO;
import com.example.bazaarstore.model.entity.Product;
import com.example.bazaarstore.service.MainPageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("bazaar/mainpage")
public class MainController {

    private final MainPageService mainPageService;

    public MainController(MainPageService mainPageService) {
        this.mainPageService = mainPageService;
    }


    @GetMapping("")
    public ResponseEntity<?> getAll(){
        List<ProductShowDTO> productList = mainPageService.userPage();
        return ResponseEntity.ok(productList);
    }

}
