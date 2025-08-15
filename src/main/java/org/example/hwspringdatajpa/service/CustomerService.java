package org.example.hwspringdatajpa.service;

import jakarta.validation.Valid;
import org.example.hwspringdatajpa.model.dto.request.CustomerRequest;
import org.example.hwspringdatajpa.model.dto.response.CustomerResponse;
import org.example.hwspringdatajpa.model.dto.response.PayloadResponse;
import org.example.hwspringdatajpa.model.dto.response.ProductResponse;
import org.example.hwspringdatajpa.model.entity.Customer;
import org.example.hwspringdatajpa.model.enums.CustomerProperty;
import org.example.hwspringdatajpa.model.enums.ProductProperty;
import org.springframework.data.domain.Sort;

public interface CustomerService {
    PayloadResponse<CustomerResponse> getAllCustomers(Integer page, Integer size, CustomerProperty customerProperty, Sort.Direction direction);

    Customer getCustomerById(Long customerId);

    CustomerResponse createCustomer( CustomerRequest customerRequest);

    void deleteCustomerById(Long customerId);

    CustomerResponse updateCustomerById(Long customerId,  CustomerRequest customerRequest);
}
