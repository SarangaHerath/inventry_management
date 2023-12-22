package com.novig.agency_management_system.dto.requestDto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestCreditDateRangeDto {
    private LocalDate fromDate;
    private LocalDate toDate;

}
