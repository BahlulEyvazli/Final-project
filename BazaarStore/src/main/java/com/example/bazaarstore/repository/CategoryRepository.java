package com.example.bazaarstore.repository;

import com.example.bazaarstore.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    Optional<Category> findCategoryByCategoryName(String categoryName);

}
