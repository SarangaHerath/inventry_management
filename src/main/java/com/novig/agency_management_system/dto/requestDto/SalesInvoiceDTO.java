package com.novig.agency_management_system.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SalesInvoiceDTO {
    private Long id;
    private Long shopId;
    private Double freeItems;
    private String paymentMethod;
    private Double discount;
    private List<SalesInvoiceDetailsDTO> salesInvoiceDetails;
}
