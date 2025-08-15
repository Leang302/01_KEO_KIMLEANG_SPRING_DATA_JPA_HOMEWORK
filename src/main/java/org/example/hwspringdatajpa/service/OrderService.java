package org.example.hwspringdatajpa.service;

import org.example.hwspringdatajpa.model.dto.request.OrderItemRequest;
import org.example.hwspringdatajpa.model.dto.response.OrderResponse;
import org.example.hwspringdatajpa.model.dto.response.PayloadResponse;
import org.example.hwspringdatajpa.model.entity.Order;
import org.example.hwspringdatajpa.model.enums.OrderProperty;
import org.example.hwspringdatajpa.model.enums.Status;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface OrderService {
    Order getOrderById(Long orderId);

    PayloadResponse<OrderResponse> getAllOrders(Long customerId, Integer page, Integer size, OrderProperty orderProperty, Sort.Direction direction);

    void deleteOrderById(Long orderId);

    Order createOrder(Long customerId, List<OrderItemRequest> ordersRequest);

    OrderResponse updateOrderById(Long orderId,  Status status);
}
