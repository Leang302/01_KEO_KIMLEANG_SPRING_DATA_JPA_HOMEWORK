package org.example.hwspringdatajpa.model.dto.request;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.hwspringdatajpa.model.entity.Account;
import org.example.hwspringdatajpa.model.entity.Customer;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerRequest {

    @Schema(description = "Customer name", example = "John")
    @NotBlank
    @NotEmpty
    @Size(max = 255, message = "Name must not be longer than 255 characters")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Name must contain only letters")
    private String name;

    @Schema(description = "Customer address", example = "Phnom penh, Cambodia")
    @NotBlank
    @NotEmpty
    @Size(max = 255, message = "Address must not be longer than 255 characters")
    @Pattern(regexp = "^[A-Za-z0-9#, ]+$", message = "Address must contain only letters, numbers, #, , and spaces")
    private String address;

    @Schema(description = "Customer phone number", example = "+855 12 123 123")
    @NotBlank
    @NotEmpty
    @Size(max = 20, message = "Phone number must not be longer than 20 characters")
    @Pattern(regexp = "^[0-9+ ]+$", message = "Phone number can only contain digits, spaces, and '+'")
    private String phoneNumber;

    @JsonUnwrapped
    private AccountRequest accountRequest;

    public Customer toEntity() {
        Customer customer = Customer.builder()
                .name(name)
                .address(address)
                .phoneNumber(phoneNumber)
                .build();
        Account account = accountRequest.toEntity();

        account.setCustomer(customer);
        customer.setAccount(account);
        return customer;
    }
}
