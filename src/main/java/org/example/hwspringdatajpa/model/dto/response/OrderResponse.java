package org.example.hwspringdatajpa.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.hwspringdatajpa.model.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private Long orderId;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private Status status;
    private CustomerResponse customer;
    private List<ProductResponse> productResponse;
}
