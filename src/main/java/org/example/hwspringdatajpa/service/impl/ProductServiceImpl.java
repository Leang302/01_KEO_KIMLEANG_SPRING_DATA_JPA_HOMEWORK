package org.example.hwspringdatajpa.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.hwspringdatajpa.exception.NotFoundException;
import org.example.hwspringdatajpa.model.dto.request.ProductRequest;
import org.example.hwspringdatajpa.model.dto.response.PaginationResponse;
import org.example.hwspringdatajpa.model.dto.response.PayloadResponse;
import org.example.hwspringdatajpa.model.dto.response.ProductResponse;
import org.example.hwspringdatajpa.model.entity.Product;
import org.example.hwspringdatajpa.model.enums.ProductProperty;
import org.example.hwspringdatajpa.repository.ProductRepository;
import org.example.hwspringdatajpa.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    @Override
    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Product with id "+productId+" not found."));
    }

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        return productRepository.save(productRequest.toEntity()).toResponse();
    }

    @Override
    public ProductResponse updateProductById(Long productId, ProductRequest productRequest) {
        Product productById = getProductById(productId);
        productById.setName(productRequest.getName());
        productById.setUnitPrice(productRequest.getUnitPrice());
        productById.setDescription(productRequest.getDescription());
        return productRepository.save(productById).toResponse();
    }

    @Override
    public void deleteProductById(Long productId) {
       productRepository.delete(getProductById(productId));
    }


    @Override
    public PayloadResponse<ProductResponse> getAllProducts(Integer page, Integer size, ProductProperty productProperty, Sort.Direction direction) {
        Page<Product> allProducts = productRepository.findAll(PageRequest.of(page-1, size, Sort.by(direction, productProperty.getFieldName())));
        return PayloadResponse.<ProductResponse>builder()
                .items(allProducts.getContent().stream().map(Product::toResponse).toList())
                .pagination(PaginationResponse.fromPage(allProducts,page,size))
                .build();
    }
}
