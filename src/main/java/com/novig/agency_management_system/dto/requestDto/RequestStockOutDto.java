package com.novig.agency_management_system.dto.requestDto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
