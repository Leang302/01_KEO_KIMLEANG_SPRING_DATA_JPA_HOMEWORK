package org.example.hwspringdatajpa.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.hwspringdatajpa.model.dto.response.OrderResponse;
import org.example.hwspringdatajpa.model.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL,orphanRemoval = true)
    @Builder.Default
    private List<OrderItem> orderItems = new ArrayList<>();

    public OrderResponse toResponse() {
        return OrderResponse.builder()
                .orderId(id)
                .orderDate(orderDate)
                .totalAmount(totalAmount)
                .status(status)
                .customer(customer.toResponse())
                .productResponse(orderItems.stream().map(orderItem -> orderItem.getProduct().toResponse()).toList())
                .build();
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
}
