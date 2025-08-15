package org.example.hwspringdatajpa.model.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    private Long id;
    private String name;
    private BigDecimal unitPrice;
    private String description;
}
