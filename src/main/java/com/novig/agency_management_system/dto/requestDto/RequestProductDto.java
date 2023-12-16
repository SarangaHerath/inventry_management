package com.novig.agency_management_system.dto.requestDto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RequestProductDto {

    private Long productId;

    private String productName;

    private String weight;

    private LocalDate date;

    private Double unitPrice;

    private int quantity;
}
