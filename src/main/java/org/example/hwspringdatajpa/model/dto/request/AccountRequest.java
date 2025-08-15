package org.example.hwspringdatajpa.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.hwspringdatajpa.model.entity.Account;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountRequest {
    @Schema(description = "Customer account username", example = "@John")
    @NotBlank
    @NotEmpty
    @Size(max = 50, message = "Username must not be longer than 50 characters")
    private String username;

    @Schema(description = "Customer account password", example = "John@123")
    @NotBlank
    @NotEmpty
    private String password;

    public Account toEntity() {
        return Account.builder()
                .username(username)
                .password(password)
                .build();
    }
}
