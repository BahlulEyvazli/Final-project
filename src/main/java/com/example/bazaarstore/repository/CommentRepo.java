package com.example.bazaarstore.repository;

import com.example.bazaarstore.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Long> {
    List<Comment> findCommentByUser_Id(long id);
    void deleteCommentById(long id);
}
