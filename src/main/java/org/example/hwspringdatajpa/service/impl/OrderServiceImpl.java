package org.example.hwspringdatajpa.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.hwspringdatajpa.exception.NotFoundException;
import org.example.hwspringdatajpa.model.dto.request.OrderItemRequest;
import org.example.hwspringdatajpa.model.dto.response.OrderResponse;
import org.example.hwspringdatajpa.model.dto.response.PaginationResponse;
import org.example.hwspringdatajpa.model.dto.response.PayloadResponse;
import org.example.hwspringdatajpa.model.entity.Customer;
import org.example.hwspringdatajpa.model.entity.Order;
import org.example.hwspringdatajpa.model.entity.Product;
import org.example.hwspringdatajpa.model.enums.OrderProperty;
import org.example.hwspringdatajpa.model.enums.Status;
import org.example.hwspringdatajpa.repository.OrderRepository;
import org.example.hwspringdatajpa.service.CustomerService;
import org.example.hwspringdatajpa.service.OrderService;
import org.example.hwspringdatajpa.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final CustomerService customerService;

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("Order with id " + orderId + " not found."));
    }

    @Override
    public PayloadResponse<OrderResponse> getAllOrders(Long customerId, Integer page, Integer size, OrderProperty orderProperty, Sort.Direction direction) {
        customerService.getCustomerById(customerId);
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(direction, orderProperty.getFieldName()));
        Page<Order> allOrders = orderRepository.findAllByCustomerId(customerId,pageable);
        return PayloadResponse.<OrderResponse>builder()
                .items(allOrders.getContent().stream().map((Order::toResponse)).toList())
                .pagination(PaginationResponse.fromPage(allOrders,page,size))
                .build();
    }

    @Override
    public void deleteOrderById(Long orderId) {
        orderRepository.delete(getOrderById(orderId));
    }

    @Override
    public Order createOrder(Long customerId, List<OrderItemRequest> ordersRequest) {
        Customer customerById = customerService.getCustomerById(customerId);
        List<Product> products = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;
        Map<Long,Integer> quantityMap = new HashMap<>();

        for (OrderItemRequest req : ordersRequest) {
            Product product = productService.getProductById(req.getProductId());
            products.add(product);
            totalAmount = totalAmount.add(product.getUnitPrice()
                    .multiply(BigDecimal.valueOf(req.getQuantity())));
            quantityMap.put(product.getId(),req.getQuantity());
        }
        return orderRepository.save(OrderItemRequest.toEntity(customerById,products,totalAmount,quantityMap));
    }

    @Override
    public OrderResponse updateOrderById(Long orderId,  Status status) {
        Order orderById = getOrderById(orderId);
        orderById.setStatus(status);
        return orderRepository.save(orderById).toResponse();
    }

}
