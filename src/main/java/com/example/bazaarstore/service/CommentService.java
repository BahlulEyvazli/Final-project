package com.example.bazaarstore.service;

import com.example.bazaarstore.model.entity.Comment;
import com.example.bazaarstore.model.entity.Product;
import com.example.bazaarstore.model.entity.User;
import com.example.bazaarstore.repository.CommentRepository;
import com.example.bazaarstore.repository.ProductRepository;
import com.example.bazaarstore.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    private final JwtService jwtService;

    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CommentService(CommentRepository commentRepository, JwtService jwtService, UserRepository userRepository,
                          ProductRepository productRepository) {
        this.commentRepository = commentRepository;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }



    public Comment sendComment(String content,String token,Long productId){
            User user = userRepository.findByUsername(jwtService.extractUserName(token)).orElseThrow();
            Product product = productRepository.findById(productId).orElseThrow();

            Comment comment = Comment.builder().content(content).product(product).user(user).build();

            return commentRepository.save(comment);
    }

}
