package com.example.bazaarstore.controller;

import com.example.bazaarstore.dto.comment.CommentDTO;
import com.example.bazaarstore.model.entity.Comment;
import com.example.bazaarstore.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//must be secure
@RestController
@RequestMapping("/bazaar/products/{productId}/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("")
    public ResponseEntity<?> sendComment(@RequestBody String text,@PathVariable("productId") Long productId){
        CommentDTO comment = commentService.sendComment(text,productId);
        return ResponseEntity.ok(comment);
    }

    @GetMapping("")
    public ResponseEntity<?> getComments(@PathVariable("productId") Long productId){
        List<CommentDTO> commentDTOList = commentService.getComments(productId);
        return ResponseEntity.ok(commentDTOList);
    }
}
