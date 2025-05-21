package com.example.jpashopdemo.domain.repository.repository;

import com.example.jpashopdemo.domain.entity.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
