package org.example.hwspringdatajpa.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.example.hwspringdatajpa.model.dto.request.CustomerRequest;
import org.example.hwspringdatajpa.model.dto.response.ApiResponse;
import org.example.hwspringdatajpa.model.dto.response.CustomerResponse;
import org.example.hwspringdatajpa.model.dto.response.PayloadResponse;
import org.example.hwspringdatajpa.model.enums.CustomerProperty;
import org.example.hwspringdatajpa.service.CustomerService;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
@Tag(name = "Customer")
public class CustomerController {
    public final CustomerService customerService;

    @Operation(summary = "Get paginated list of customers",description = "Retrieves all customers in a paginated format. You can specify the page number, page size, sorting property, and sort direction.")
    @GetMapping
    public ResponseEntity<ApiResponse<PayloadResponse<CustomerResponse>>> getAllCustomers(@Positive @RequestParam(defaultValue = "1") Integer page,@Positive @RequestParam(defaultValue = "10") Integer size, @RequestParam CustomerProperty customerProperty, @RequestParam Sort.Direction direction) {
        return ResponseEntity.ok(
                ApiResponse.<PayloadResponse<CustomerResponse>>builder()
                        .code(HttpStatus.OK)
                        .payload(customerService.getAllCustomers(page, size, customerProperty, direction))
                        .message("All products fetched successfully.")
                        .build()
        );
    }

    @Operation(summary = "Get Customer by id",description = "Fetches detailed information about a single customer using the provided customer ID.")
    @GetMapping("/{customer-id}")
    public ResponseEntity<ApiResponse<CustomerResponse>> getCustomerById(@PathVariable("customer-id") Long customerId) {
        return ResponseEntity.ok(
                ApiResponse.<CustomerResponse>builder()
                        .code(HttpStatus.OK)
                        .payload(customerService.getCustomerById(customerId).toResponse())
                        .message("Customer by id fetched successfully.")
                        .build()
        );
    }

    @Operation(summary = "Create a new Customer",description = "Accepts a valid `CustomerRequest` object to create a new customer record. Returns the newly created customer's details.")
    @PostMapping
    public ResponseEntity<ApiResponse<CustomerResponse>> createCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<CustomerResponse>builder()
                        .code(HttpStatus.OK)
                        .payload(customerService.createCustomer(customerRequest))
                        .message("Customer created successfully.")
                        .build()
        );
    }

    @Operation(summary = "Update Customer by id",description = "Updates the details of an existing customer based on the provided customer ID and request body.")
    @PutMapping("/{customer-id}")
    public ResponseEntity<ApiResponse<CustomerResponse>> updateCustomerById(@PathVariable("customer-id") Long customerId, @Valid @RequestBody CustomerRequest customerRequest) {
        return ResponseEntity.ok(
                ApiResponse.<CustomerResponse>builder()
                        .code(HttpStatus.OK)
                        .payload(customerService.updateCustomerById(customerId, customerRequest))
                        .message("Customer by id updated successfully.")
                        .build()
        );
    }

    @Operation(summary = "Delete Customer by id",description = "Removes a customer record from the system based on the provided customer ID.")
    @DeleteMapping("/{customer-id}")
    public ResponseEntity<ApiResponse<?>> deleteCustomerById(@PathVariable("customer-id") Long customerId) {
        customerService.deleteCustomerById(customerId);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .code(HttpStatus.OK)
                        .message("Customer by id deleted successfully.")
                        .build()
        );
    }
}
