package org.example.hwspringdatajpa.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "order_items")
public class OrderItem {
    @EmbeddedId
    private OrderItemId id;
    @ManyToOne(optional = false)
    @MapsId("orderId")
    private Order order;
    @ManyToOne
    @MapsId("productId")
    private Product product;
    private Integer quantity;
}
