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
    private Long saleInvoiceId;
    private Long shopId;
    private String shopName;
    private Double totalAmount;
    private Double discount;
    private String paymentType;

}
