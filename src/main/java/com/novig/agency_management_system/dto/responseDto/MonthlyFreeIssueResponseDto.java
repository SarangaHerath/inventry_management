package com.novig.agency_management_system.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MonthlyFreeIssueResponseDto {
    private String productName;
    private int quantity;

}
