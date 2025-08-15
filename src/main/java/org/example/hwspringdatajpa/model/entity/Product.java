package org.example.hwspringdatajpa.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.hwspringdatajpa.model.dto.response.ProductResponse;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal unitPrice;
    private String description;
    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    public ProductResponse toResponse() {
        return ProductResponse.builder()
                .id(id)
                .name(name)
                .unitPrice(unitPrice)
                .description(description)
                .build();
    }

}

