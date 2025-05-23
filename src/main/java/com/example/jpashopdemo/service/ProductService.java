package com.example.jpashopdemo.service;

import com.example.jpashopdemo.domain.entity.entity.Category;
import com.example.jpashopdemo.domain.entity.entity.Product;
import com.example.jpashopdemo.domain.repository.repository.CategoryRepository;
import com.example.jpashopdemo.domain.repository.repository.ProductRepository;
import com.example.jpashopdemo.dto.ProductDto;
import com.example.jpashopdemo.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public List<ProductDto> getAllProducts(){
        return productRepository.findAll().stream()
                .map(productMapper::toProductDto)
                .toList();
    };

    @Transactional
    public ProductDto createProduct(ProductDto productDto) {
        productDto.setId(null);

        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Category not found with id: " + productDto.getCategoryId()));

        Product product = productMapper.toEntity(productDto);
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);
        return productMapper.toProductDto(savedProduct);
    }


    public ProductDto getProductByID(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id: " + id));
        return productMapper.toProductDto(product);
    }

    public ProductDto getProductByName(String name) {
        Product product = productRepository.findByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with name: " + name));
        return productMapper.toProductDto(product);
    }

    @Transactional
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Product findProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id: " + id));

        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Category not found with id: " + productDto.getCategoryId()));

        productDto.setId(findProduct.getId());
        Product entity = productMapper.toEntity(productDto);
        entity.setCategory(category);

        Product savedProduct = productRepository.save(entity);
        return productMapper.toProductDto(savedProduct);
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product findProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id: " + id));
        productRepository.delete(findProduct);
    }

    @Transactional
    public ProductDto updateProductStock(Long id, int stock) {
        Product findProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id: " + id));

        findProduct.setStock(stock);
        Product savedProduct = productRepository.save(findProduct);
        return productMapper.toProductDto(savedProduct);
    }

    public List<ProductDto> findProductsByPriceRange(Integer minPrice, Integer maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice).stream()
                .map(productMapper::toProductDto)
                .toList();
    }
}
