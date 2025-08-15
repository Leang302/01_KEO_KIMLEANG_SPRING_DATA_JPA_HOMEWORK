package org.example.hwspringdatajpa.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import org.example.hwspringdatajpa.model.entity.*;
import org.example.hwspringdatajpa.model.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemRequest {
    @Schema(description = "Customer name", example = "1")
    @NotNull
    @Min(value=1)
    @Max(value = 999999999)
    private Integer quantity;
    @NotNull
    private Long productId;

    public static Order toEntity(Customer customer, List<Product> products, BigDecimal totalAmount, Map<Long,Integer> quantityMap) {
        Order order = Order.builder()
                .totalAmount(totalAmount)
                .status(Status.PENDING)
                .customer(customer)
                .orderDate(LocalDateTime.now())
                .build();
        products.stream()
                .map(product -> OrderItem.builder()
                        .id(new OrderItemId())
                        .product(product)
                        .order(order)
                        .quantity(quantityMap.get(product.getId()))
                        .build())
                .forEach(order::addOrderItem);
        return order;
    }
}
