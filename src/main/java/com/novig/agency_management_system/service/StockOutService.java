package com.novig.agency_management_system.service;

import com.novig.agency_management_system.dto.requestDto.RequestStockOutDto;
import com.novig.agency_management_system.entity.Product;
import com.novig.agency_management_system.entity.StockOut;

import java.util.List;
import java.util.Optional;

public interface StockOutService {
    List<StockOut> getAllStockOut();

    String deleteStockOut(Long id);

    StockOut updateStockOut(RequestStockOutDto requestStockOutDto);

    StockOut addStockOut(RequestStockOutDto requestStockOutDto);
    List<StockOut> getOutOfStockProductDetails(Long productId);
}
