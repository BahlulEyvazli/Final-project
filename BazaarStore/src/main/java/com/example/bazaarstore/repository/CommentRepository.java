package com.example.bazaarstore.repository;

import com.example.bazaarstore.dto.comment.CommentDTO;
import com.example.bazaarstore.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {


    List<Comment> findAllByProductId(Long productId);
}
