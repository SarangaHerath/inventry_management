package com.novig.agency_management_system.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RequestSaleDto {

    private List<ProductDto> products;

    private Long shopId;
}

