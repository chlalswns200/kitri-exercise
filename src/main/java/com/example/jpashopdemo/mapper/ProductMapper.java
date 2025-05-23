package com.example.jpashopdemo.mapper;

import com.example.jpashopdemo.domain.entity.entity.Product;
import com.example.jpashopdemo.dto.ProductDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toProductDto(Product product);
    Product toEntity(ProductDto productDto);
}
