package com.novig.agency_management_system.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryWiseProductDto {
    private Long productId;
    private String productName;
    private int quantity;
    private Double unitPrice;
    private String weight;

}
