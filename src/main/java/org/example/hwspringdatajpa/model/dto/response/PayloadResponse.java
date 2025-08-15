package org.example.hwspringdatajpa.model.dto.response;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayloadResponse <T> {
    private List<T> items;
    private PaginationResponse pagination;
}
