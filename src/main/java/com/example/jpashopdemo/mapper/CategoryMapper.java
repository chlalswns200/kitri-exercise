package com.example.jpashopdemo.mapper;

import com.example.jpashopdemo.domain.entity.entity.Category;
import com.example.jpashopdemo.dto.CategoryDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toCategoryDto(Category category);
    Category toEntity(CategoryDto dto);
}
