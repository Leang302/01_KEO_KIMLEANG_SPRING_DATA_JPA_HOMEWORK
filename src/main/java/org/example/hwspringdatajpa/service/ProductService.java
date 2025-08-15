package org.example.hwspringdatajpa.service;

import jakarta.validation.Valid;
import org.example.hwspringdatajpa.model.dto.request.ProductRequest;
import org.example.hwspringdatajpa.model.dto.response.PayloadResponse;
import org.example.hwspringdatajpa.model.dto.response.ProductResponse;
import org.example.hwspringdatajpa.model.entity.Product;
import org.example.hwspringdatajpa.model.enums.ProductProperty;
import org.springframework.data.domain.Sort;

public interface ProductService {
    Product getProductById(Long productId);

    ProductResponse createProduct(ProductRequest productRequest);

    ProductResponse updateProductById(Long productId, ProductRequest productRequest);

    void deleteProductById(Long productId);

    PayloadResponse<ProductResponse> getAllProducts(Integer page, Integer size, ProductProperty productProperty, Sort.Direction direction);
}
