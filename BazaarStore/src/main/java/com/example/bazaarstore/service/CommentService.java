package com.example.bazaarstore.service;

import com.example.bazaarstore.dto.comment.CommentDTO;
import com.example.bazaarstore.dto.product.ProductDTO;
import com.example.bazaarstore.model.entity.Comment;
import com.example.bazaarstore.model.entity.Product;
import com.example.bazaarstore.model.entity.User;
import com.example.bazaarstore.repository.CommentRepository;
import com.example.bazaarstore.repository.ProductRepository;
import com.example.bazaarstore.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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



    public CommentDTO sendComment(String content, Long productId){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();
        Comment comment = Comment.builder().content(content).product(product).user(user).build();
        commentRepository.save(comment);

        return CommentDTO.builder().username(user.getUsername()).productName(product.getName())
                .content(comment.getContent()).build();
    }

    public List<CommentDTO> getComments(Long productId){
        Product product = productRepository.findById(productId).orElseThrow();
        List<Comment> commentList = commentRepository.findAllByProductId(productId);
        return commentList.stream().map(comment -> new CommentDTO(comment.getContent(),
                comment.getUser().getUsername(), product.getName())).toList();
    }

}
