package org.example.hwspringdatajpa.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.example.hwspringdatajpa.model.dto.request.ProductRequest;
import org.example.hwspringdatajpa.model.dto.response.ApiResponse;
import org.example.hwspringdatajpa.model.dto.response.PayloadResponse;
import org.example.hwspringdatajpa.model.dto.response.ProductResponse;
import org.example.hwspringdatajpa.model.enums.ProductProperty;
import org.example.hwspringdatajpa.service.ProductService;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
@Tag(name = "Product")
public class ProductController {
    private final ProductService productService;

    @Operation(summary = "Get paginated list of products",description = "Retrieves a list of products in paginated format. You can specify the page number, page size, sorting property (from `ProductProperty` enum), and sort direction.")
    @GetMapping
    public ResponseEntity<ApiResponse<PayloadResponse<ProductResponse>>> getAllProducts(@Positive @RequestParam(defaultValue = "1") Integer page,@Positive @RequestParam(defaultValue = "10") Integer size, @RequestParam ProductProperty productProperty, @RequestParam Sort.Direction direction) {
        return ResponseEntity.ok(
                ApiResponse.<PayloadResponse<ProductResponse>>builder()
                        .code(HttpStatus.OK)
                        .payload(productService.getAllProducts(page, size, productProperty, direction))
                        .message("All products fetched successfully.")
                        .build()
        );
    }

    @Operation(summary = "Get product by id",description = "Retrieves the details of a product using the specified product ID.")
    @GetMapping("/{product-id}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductById(@PathVariable("product-id") Long productId) {
        return ResponseEntity.ok(
                ApiResponse.<ProductResponse>builder()
                        .code(HttpStatus.OK)
                        .payload(productService.getProductById(productId).toResponse())
                        .message("Product by id fetched successfully.")
                        .build()
        );
    }

    @Operation(summary = "Create a new product",description = "Creates a new product record in the system using the provided `ProductRequest` payload. Returns the details of the newly created product.")
    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> getProductById(@Valid @RequestBody ProductRequest productRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<ProductResponse>builder()
                        .code(HttpStatus.OK)
                        .payload(productService.createProduct(productRequest))
                        .message("Product created successfully.")
                        .build()
        );
    }

    @Operation(summary = "Update product by id",description = "Updates the details of an existing product using the provided product ID and `ProductRequest` payload.")
    @PutMapping("/{product-id}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProductById(@PathVariable("product-id") Long productId, @Valid @RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok(
                ApiResponse.<ProductResponse>builder()
                        .code(HttpStatus.OK)
                        .payload(productService.updateProductById(productId, productRequest))
                        .message("Product by id updated successfully.")
                        .build()
        );
    }

    @Operation(summary = "Delete product by id",description = "Deletes an existing product from the system using the specified product ID.")
    @DeleteMapping("/{product-id}")
    public ResponseEntity<ApiResponse<?>> deleteProductById(@PathVariable("product-id") Long productId) {
        productService.deleteProductById(productId);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .code(HttpStatus.OK)
                        .message("Product by id deleted successfully.")
                        .build()
        );
    }
}
