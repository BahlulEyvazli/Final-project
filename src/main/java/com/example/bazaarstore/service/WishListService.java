package com.example.bazaarstore.service;


import com.example.bazaarstore.repository.WishListRepository;
import org.springframework.stereotype.Service;

@Service
public class WishListService {

    private WishListRepository wishListRepository;

    public WishListService(WishListRepository wishListRepository) {
        this.wishListRepository = wishListRepository;
    }


}
