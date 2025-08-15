package org.example.hwspringdatajpa.model.dto.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponse {
    private Long customerId;
    private String name;
    private String address;
    private String phoneNumber;
    @JsonUnwrapped
    private AccountResponse accountResponse;
}
