package org.example.hwspringdatajpa.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.hwspringdatajpa.exception.BadRequestException;
import org.example.hwspringdatajpa.exception.NotFoundException;
import org.example.hwspringdatajpa.model.dto.request.CustomerRequest;
import org.example.hwspringdatajpa.model.dto.response.CustomerResponse;
import org.example.hwspringdatajpa.model.dto.response.PaginationResponse;
import org.example.hwspringdatajpa.model.dto.response.PayloadResponse;
import org.example.hwspringdatajpa.model.entity.Account;
import org.example.hwspringdatajpa.model.entity.Customer;
import org.example.hwspringdatajpa.model.enums.CustomerProperty;
import org.example.hwspringdatajpa.repository.CustomerRepository;
import org.example.hwspringdatajpa.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    @Override
    public PayloadResponse<CustomerResponse> getAllCustomers(Integer page, Integer size, CustomerProperty customerProperty, Sort.Direction direction) {
        Page<Customer> allCustomers = customerRepository.findAll(PageRequest.of(page - 1, size, Sort.by(direction, customerProperty.getFieldName())));
        return PayloadResponse.<CustomerResponse>builder()
                .items(allCustomers.getContent().stream().map(Customer::toResponse).toList())
                .pagination(PaginationResponse.fromPage(allCustomers,page,size))
                .build();
    }

    @Override
    public Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId).orElseThrow(() -> new NotFoundException("Customer with id "+customerId+" not found."));
    }

    @Override
    public CustomerResponse createCustomer(CustomerRequest customerRequest) {
        if(customerRepository.existsByAccountUsername(customerRequest.getAccountRequest().getUsername())){
            throw new BadRequestException("User with username `"+customerRequest.getAccountRequest().getUsername()+"` already exist.");
        }
        return customerRepository.save(customerRequest.toEntity()).toResponse();
    }

    @Override
    public void deleteCustomerById(Long customerId) {
        customerRepository.delete(getCustomerById(customerId));
    }

    @Override
    public CustomerResponse updateCustomerById(Long customerId, CustomerRequest customerRequest) {
        if(customerRepository.existsByAccountUsernameAndIdNot(customerRequest.getAccountRequest().getUsername(),customerId)){
            throw new BadRequestException("User with username `"+customerRequest.getAccountRequest().getUsername()+"` already exist.");
        }
        Customer customerById = getCustomerById(customerId);
        Account account = customerById.getAccount();
        customerById.setName(customerRequest.getName());
        customerById.setAddress(customerRequest.getAddress());
        customerById.setPhoneNumber(customerRequest.getPhoneNumber());
        account.setUsername(customerRequest.getAccountRequest().getUsername());
        account.setPassword(customerRequest.getAccountRequest().getPassword());
        return customerRepository.save(customerById).toResponse();
    }



}
