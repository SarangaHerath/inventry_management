package com.novig.agency_management_system.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestCreditPaymentDto {
    private Long creditId;
    private Long shop_id;
    private LocalDate billDate;
    private Double creditAmount;
    private Double paidAmount;
    private LocalDate lastPaymentDate;
}
