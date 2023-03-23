package com.example.bazaarstore.service;
import com.example.bazaarstore.model.entity.Category;
import com.example.bazaarstore.model.entity.Product;
import com.example.bazaarstore.model.entity.User;
import com.example.bazaarstore.model.entity.WishList;
import com.example.bazaarstore.repository.CategoryRepository;
import com.example.bazaarstore.repository.UserRepository;
import com.example.bazaarstore.repository.WishListRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class MainPageService {
    private final UserRepository userRepository;

    private final WishListRepository wishListRepository;

    private final JwtService jwtService;

    private final CategoryRepository categoryRepository;

    public MainPageService(WishListRepository wishListRepository,
                           UserRepository userRepository, JwtService jwtService,
                           CategoryRepository categoryRepository) {
        this.wishListRepository = wishListRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.categoryRepository = categoryRepository;
    }


    public List<Product> userPage() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        HashMap<Category, Integer> wishesCategory = takeFavoriteCategory(user);
        List<Category> categories = sortedFavorites(wishesCategory);
        List<Product> productList = new ArrayList<>();
        for (Category category : categories){
            log.info("CATEGORY :" + categoryRepository.findCategoryByCategoryName(category.getCategoryName()).orElseThrow().getProducts());
            productList.addAll(category.getProducts());
        }
        log.info("LIST : " + productList);
        return productList;
    }

    private HashMap<Category,Integer> takeFavoriteCategory(User user) {
        HashMap<Category,Integer> categories = new HashMap<>();
        List<WishList> wishes = wishListRepository.findAllByUser(user);

        for (WishList wish : wishes){
            Category category = wish.getProduct().getCategory();
            if (categories.containsKey(category)){
                categories.put(category,categories.get(category)+1);
            }
            else {
                categories.put(category,1);
            }
        }
        for (Map.Entry<Category,Integer> entry : categories.entrySet()){
            log.info("Key :" + entry.getKey());
            log.info("Key :" + entry.getValue());
        }
        return categories;
    }

    private Category findMostAdded(HashMap<Category,Integer> categoryMap){
        Category mostUsed = null;
        int max = 0;

        for (Map.Entry<Category,Integer> entry : categoryMap.entrySet()){
            if (entry.getValue()>max){
                mostUsed = entry.getKey();
                max=entry.getValue();
            }
        }
        return mostUsed;
    }

    private List<Category> sortedFavorites(HashMap<Category,Integer> map){

        List<Category> result = new ArrayList<>();
        while (!map.isEmpty()){
            result.add(findMostAdded(map));
            map.remove(findMostAdded(map));
        }
        return result;
    }

}