package com.novig.agency_management_system.dto.requestDto;

import com.novig.agency_management_system.entity.Shop;
import jakarta.persistence.*;
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
    private Long paymentMethodId;
    private Long discountId;
    private List<SalesInvoiceDetailsDTO> salesInvoiceDetails;

}
