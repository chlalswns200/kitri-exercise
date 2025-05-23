package com.example.jpashopdemo.controller;

import com.example.jpashopdemo.dto.ProductDto;
import com.example.jpashopdemo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        ProductDto savedProduct = productService.createProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        ProductDto findProduct = productService.getProductByID(id);
        return ResponseEntity.ok(findProduct);
    }

    @GetMapping("/price")
    public ResponseEntity<List<ProductDto>> findProductsByPriceRange(
            @RequestParam Integer minPrice,
            @RequestParam Integer maxPrice) {
        List<ProductDto> findProducts = productService.findProductsByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(findProducts);
    }

    @GetMapping("/search")
    public ResponseEntity<ProductDto> getProductByName(@RequestParam String name) {
        ProductDto findProduct = productService.getProductByName(name);
        return ResponseEntity.ok(findProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id,
                                                    @RequestBody ProductDto productDto) {
        ProductDto updatedProduct = productService.updateProduct(id,productDto);
        return ResponseEntity.ok(updatedProduct);
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<ProductDto> updateProductStock(@PathVariable Long id,
                                                         @RequestParam int stock) {
        ProductDto updatedProduct = productService.updateProductStock(id, stock);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
