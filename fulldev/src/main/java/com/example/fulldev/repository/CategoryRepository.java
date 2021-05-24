package com.example.fulldev.repository;

import com.example.fulldev.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByIsRootOrderByIndex(Boolean isRoot);
}
