package com.example.bazaarstore.controller;

import com.example.bazaarstore.model.entity.Comment;
import com.example.bazaarstore.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


//must be secure
@RestController
@RequestMapping("/bazaar/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/add/{productId}")
    public ResponseEntity<?> sendComment(@RequestParam("token") String token,
                                         @RequestBody String text,@PathVariable("productId") Long productId){

        Comment comment = commentService.sendComment(text,token,productId);

        return ResponseEntity.ok(comment);
    }
}
