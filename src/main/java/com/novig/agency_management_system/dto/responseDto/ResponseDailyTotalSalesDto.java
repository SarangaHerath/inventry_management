package com.novig.agency_management_system.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseDailyTotalSalesDto {
    private Double chequeTotal;
    private Double creditTotal;
    private Double cashTotal;
    private Double total;
}
