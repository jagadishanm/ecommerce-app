package com.example.eCommerceApp.e_Commerce.Application.service;

import com.example.eCommerceApp.e_Commerce.Application.dto.request.ProductRequest;
import com.example.eCommerceApp.e_Commerce.Application.dto.response.ProductResponse;
import com.example.eCommerceApp.e_Commerce.Application.entity.Product;
import com.example.eCommerceApp.e_Commerce.Application.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    /*
        Create product
    */
    public ProductResponse createProduct(
            ProductRequest request) {

        Product product = new Product();

        product.setName(request.getName());

        product.setDescription(
                request.getDescription());

        product.setPrice(request.getPrice());

        product.setStock(request.getStock());

        Product savedProduct =
                productRepository.save(product);

        return mapToResponse(savedProduct);
    }

    /*
        Get all products
    */
    public List<ProductResponse> getAllProducts() {

        return productRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    /*
        Get product by id
    */
    public ProductResponse getProductById(Long id) {

        Product product = productRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Product not found"));

        return mapToResponse(product);
    }

    /*
        Update product
    */
    public ProductResponse updateProduct(
            Long id,
            ProductRequest request) {

        Product product = productRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Product not found"));

        product.setName(request.getName());

        product.setDescription(
                request.getDescription());

        product.setPrice(request.getPrice());

        product.setStock(request.getStock());

        Product updatedProduct =
                productRepository.save(product);

        return mapToResponse(updatedProduct);
    }

    /*
        Delete product
    */
    public void deleteProduct(Long id) {

        Product product = productRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Product not found"));

        productRepository.delete(product);
    }

    /*
        Entity -> Response DTO
    */
    private ProductResponse mapToResponse(
            Product product) {

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock()
        );
    }
}