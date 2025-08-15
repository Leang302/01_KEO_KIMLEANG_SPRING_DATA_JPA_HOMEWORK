package org.example.hwspringdatajpa.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import org.example.hwspringdatajpa.model.entity.Product;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {
    @Schema(description = "Product name", example = "Soju")
    @NotBlank
    @NotEmpty
    @Size(max = 255,message = "Name must not be longer than 255 characters")
    @Pattern(regexp = ".*[a-zA-Z].*", message = "Name must contain at least one letter (a-z or A-Z)")
    private String name;

    @Schema(description = "Product unit price ", example = "10")
    @NotNull(message = "Unit price must not be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Unit price must be greater than 0")
    @DecimalMax(value = "9999999999.99", message = "Unit price must be less than or equal to 9,999,999,999.99")
    private BigDecimal unitPrice;

    @Schema(description = "Product description", example = "Product description")
    private String description;

    public Product toEntity() {
        return Product.builder()
                .name(name)
                .unitPrice(unitPrice)
                .description(description)
                .build();
    }
}
