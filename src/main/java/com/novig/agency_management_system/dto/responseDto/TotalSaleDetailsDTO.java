package com.novig.agency_management_system.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalSaleDetailsDTO {
    Long rowCount;
    Double totalSale;
    Double totalDiscount;
    Double totalFreeItems;
    Double totalReturnValues;
    Double totalCheque;
    Double totalCredit;
    Double totalCash;

}
