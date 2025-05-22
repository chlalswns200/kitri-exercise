package com.example.jpashopdemo.service;

import com.example.jpashopdemo.domain.entity.entity.Category;
import com.example.jpashopdemo.domain.repository.repository.CategoryRepository;
import com.example.jpashopdemo.domain.repository.repository.UserRepository;
import com.example.jpashopdemo.dto.CategoryDto;
import com.example.jpashopdemo.mapper.CategoryMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    // 모든 카테고리 조회
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toCategoryDto)
                .toList();
    }

    // ID로 카테고리 조회
    public CategoryDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found with id " + id));
        return categoryMapper.toCategoryDto(category);
    }

    public CategoryDto getCategoryByName(String name) {
        Category category = categoryRepository.findCategoryByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found with name " + name));
        return categoryMapper.toCategoryDto(category);
    }

    // 신규 카테고리 생성
    @Transactional
    public CategoryDto createCategory(CategoryDto categoryDto) {
        categoryDto.setId(null);

        Category category = categoryMapper.toEntity(categoryDto);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toCategoryDto(savedCategory);
    }

    // 기존 카테고리 수정
    @Transactional
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        if (!categoryRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Category not found with id: " + id);
        }

        categoryDto.setId(id);
        Category category = categoryMapper.toEntity(categoryDto);
        Category updatedCategory = categoryRepository.save(category);
        return categoryMapper.toCategoryDto(updatedCategory);
    }

    // 카테고리 삭제
    @Transactional
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Category not found with id: " + id);}
        categoryRepository.deleteById(id);
    }
}
