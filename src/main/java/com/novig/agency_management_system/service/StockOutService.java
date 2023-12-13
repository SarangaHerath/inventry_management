package com.novig.agency_management_system.service;

import com.novig.agency_management_system.dto.requestDto.RequestStockOutDto;
import com.novig.agency_management_system.entity.StockOut;

import java.util.List;

public interface StockOutService {
    List<StockOut> getAllStockOut();

    String deleteStockOut(Long id);

    StockOut updateStockOut(RequestStockOutDto requestStockOutDto);

    StockOut addStockOut(RequestStockOutDto requestStockOutDto);
}
