package org.example.hwspringdatajpa.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "customer_accounts")
public class Account {
    @Id
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    @Builder.Default
    private Boolean isActive=true;
    @MapsId
    @JoinColumn(name="id")
    @OneToOne
    private Customer customer;
}
