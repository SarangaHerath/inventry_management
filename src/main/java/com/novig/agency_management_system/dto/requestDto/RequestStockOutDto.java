package com.novig.agency_management_system.dto.requestDto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RequestStockOutDto {

    private Long stockOutId;
    private Long productId;
    private Long vehicleId;
    private int quantity;
    private LocalDate dateOut;

}
