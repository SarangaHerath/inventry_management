package com.novig.agency_management_system.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SalesInvoiceDetailsDTO {
    private Long id;
    private Long salesInvoiceId;
    private ProductDto product;
    private int quantity;
    private Double unitPrice;
}
