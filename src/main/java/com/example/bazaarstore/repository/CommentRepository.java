package com.example.bazaarstore.repository;

import com.example.bazaarstore.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {


}
