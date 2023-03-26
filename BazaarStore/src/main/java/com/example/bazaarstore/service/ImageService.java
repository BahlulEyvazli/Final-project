package com.example.bazaarstore.service;


import com.example.bazaarstore.model.entity.Product;
import com.example.bazaarstore.model.entity.User;
import com.example.bazaarstore.repository.ProductRepository;
import com.example.bazaarstore.repository.UserRepository;
import com.example.bazaarstore.util.ImageUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ImageService(ProductRepository productRepository,
                        UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }


    @Transactional
    public byte[] uploadImage(MultipartFile file,Long productId) throws IOException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Product product = productRepository.findProductByIdAndActiveIsTrue(productId);
        if (product.getUser().getUsername().equals(userDetails.getUsername())){
            byte[] image = file.getBytes();
            product.setImage(image);
            productRepository.save(product);
            return image;
        }
        else {

            return null;
        }
    }

}
