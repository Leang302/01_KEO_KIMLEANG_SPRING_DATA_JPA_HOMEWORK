package org.example.hwspringdatajpa.repository;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.example.hwspringdatajpa.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Boolean existsByAccountUsername(String username);


    Boolean existsByAccountUsernameAndIdNot(String username,Long id);
}
