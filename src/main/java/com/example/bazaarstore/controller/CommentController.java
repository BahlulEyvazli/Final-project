package com.example.bazaarstore.controller;

import com.example.bazaarstore.model.entity.Comment;
import com.example.bazaarstore.model.entity.Product;
import com.example.bazaarstore.model.entity.User;
import com.example.bazaarstore.service.CommentService;
import com.example.bazaarstore.service.ProductService;
import com.example.bazaarstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bazaar/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;
    private final ProductService productService;
    @GetMapping("/getall")
    public List<Comment> getAllComments(){
        return commentService.getAllComments();
    }

    @PostMapping("/create/{userId}/{productId}")
    public ResponseEntity<Comment> saveComment(@PathVariable("userId") long userId,
                                               @PathVariable("productId") long productId,
                                               @RequestBody Comment comment){
        User user = userService.getUserById(userId);
        Product product = productService.findProductById(productId);
        comment.setProduct(product);
        comment.setUser(user);
        commentService.addComment(comment);
        return ResponseEntity.created(URI.create("/comment"+ comment.getId())).body(comment);
    }

}
