package com.example.jpashopdemo.domain.repository.repository;

import com.example.jpashopdemo.domain.entity.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findCategoryByName(String name);
}
