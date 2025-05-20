package com.example.jpashopdemo.repository;

import com.example.jpashopdemo.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
