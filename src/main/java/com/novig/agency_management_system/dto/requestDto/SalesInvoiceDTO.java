package com.novig.agency_management_system.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SalesInvoiceDTO {
    private Long id;
    private Long shopId;
    private Double total;
    private Double returnValue;
    private LocalDate date;
    private Double freeItems;
    private Double cash;
    private Double credit;
    private Double cheque;
    private Double discount;
    private List<SalesInvoiceDetailsDTO> salesInvoiceDetails;

}
