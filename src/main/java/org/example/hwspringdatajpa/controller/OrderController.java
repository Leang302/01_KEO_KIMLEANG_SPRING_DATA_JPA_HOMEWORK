package org.example.hwspringdatajpa.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.hwspringdatajpa.model.dto.request.OrderItemRequest;
import org.example.hwspringdatajpa.model.dto.response.ApiResponse;
import org.example.hwspringdatajpa.model.dto.response.OrderResponse;
import org.example.hwspringdatajpa.model.dto.response.PayloadResponse;
import org.example.hwspringdatajpa.model.enums.OrderProperty;
import org.example.hwspringdatajpa.model.enums.Status;
import org.example.hwspringdatajpa.service.OrderService;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
@Tag(name = "Order")
public class OrderController {
    private final OrderService orderService;

    @Operation(summary = "Get paginated list of orders",description = "Retrieves a paginated list of orders for the specified customer. Supports page/size and sorting by `OrderProperty` with `Sort.Direction`.")
    @GetMapping("customers/{customer-id}")
    public ResponseEntity<ApiResponse<PayloadResponse<OrderResponse>>> getAllOrders(@PathVariable("customer-id") Long customerId,@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size, @RequestParam OrderProperty orderProperty, @RequestParam Sort.Direction direction) {
        return ResponseEntity.ok(
                ApiResponse.<PayloadResponse<OrderResponse>>builder()
                        .code(HttpStatus.OK)
                        .payload(orderService.getAllOrders(customerId,page, size, orderProperty, direction))
                        .message("All orders fetched successfully.")
                        .build()
        );
    }

    @Operation(summary = "Get Order by id",description = "Fetches a single order, including its line items and status, using the provided order ID.")
    @GetMapping("/{order-id}")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrderById(@PathVariable("order-id") Long orderId) {
        return ResponseEntity.ok(
                ApiResponse.<OrderResponse>builder()
                        .code(HttpStatus.OK)
                        .payload(orderService.getOrderById(orderId).toResponse())
                        .message("Order by id fetched successfully.")
                        .build()
        );
    }

    @Operation(summary = "Create a new order",description = "Creates one new order for the given customer using a non-empty list of `OrderRequest` items (each item represents an order line). Returns the created order with calculated totals.")
    @PostMapping("/customers/{customer-id}")
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(@RequestBody List<@Valid OrderItemRequest> ordersRequest, @PathVariable("customer-id") Long customerId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<OrderResponse>builder()
                        .code(HttpStatus.OK)
                        .payload(orderService.createOrder(customerId,ordersRequest).toResponse())
                        .message("Order created successfully.")
                        .build()
        );
    }

    @Operation(summary = "Update order status",description = "Updates the status of an existing order using the provided order ID and `OrderStatus` value (e.g., `PENDING`, `PAID`, `SHIPPED`, `CANCELLED`).")
    @PutMapping("/{order-id}")
    public ResponseEntity<ApiResponse<OrderResponse>> updateOrderById(@PathVariable("order-id") Long orderId,  @RequestParam Status status) {
        return ResponseEntity.ok(
                ApiResponse.<OrderResponse>builder()
                        .code(HttpStatus.OK)
                        .payload(orderService.updateOrderById(orderId, status))
                        .message("Order by id updated successfully.")
                        .build()
        );
    }

    @Operation(summary = "Delete Order by id",description = "Deletes an order from the system using the specified order ID.")
    @DeleteMapping("/{order-id}")
    public ResponseEntity<ApiResponse<?>> deleteOrderById(@PathVariable("order-id") Long orderId) {
        orderService.deleteOrderById(orderId);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .code(HttpStatus.OK)
                        .message("Order by id deleted successfully.")
                        .build()
        );
    }
}
