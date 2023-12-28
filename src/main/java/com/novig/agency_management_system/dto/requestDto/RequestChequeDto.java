package com.novig.agency_management_system.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestChequeDto {
    private Long chequeId;
    private Long shop_id;
    private String chequeNumber;
    private LocalDate receivedDate;
    private LocalDate bankDate;
    private Double amount;
    private String remark;
}

