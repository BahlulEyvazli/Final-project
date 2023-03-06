package com.example.bazaarstore.service;

import com.example.bazaarstore.model.entity.Comment;
import com.example.bazaarstore.repository.CommentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepo commentRepo;

    public List<Comment> getAllComments() {
        return commentRepo.findAll();
    }

    public List<Comment> getAllCommentsByUserID(long id) {
        return commentRepo.findCommentByUser_Id(id);
    }

    public void deleteCommentById(long id) {
        commentRepo.deleteCommentById(id);
    }

    public void addComment(Comment comment) {
        commentRepo.save(comment);
    }
}
