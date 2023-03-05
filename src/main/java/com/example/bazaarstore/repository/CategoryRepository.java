package com.example.bazaarstore.repository;

import com.example.bazaarstore.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {

}
