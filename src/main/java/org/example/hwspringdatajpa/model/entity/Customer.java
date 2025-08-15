package org.example.hwspringdatajpa.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.hwspringdatajpa.model.dto.response.AccountResponse;
import org.example.hwspringdatajpa.model.dto.response.CustomerResponse;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    private List<Order> orders;
    @OneToOne(cascade = CascadeType.ALL,mappedBy = "customer")
    private Account account;

    public CustomerResponse toResponse() {
        return CustomerResponse.builder()
                .customerId(id)
                .name(name)
                .address(address)
                .phoneNumber(phoneNumber)
                .accountResponse(AccountResponse.builder()
                        .username(account.getUsername())
                        .password(account.getPassword())
                        .isActive(account.getIsActive())
                        .build())
                .build();
    }
}
