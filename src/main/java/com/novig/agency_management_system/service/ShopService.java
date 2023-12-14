package com.novig.agency_management_system.service;

import com.novig.agency_management_system.dto.requestDto.RequestShopDto;
import com.novig.agency_management_system.entity.Shop;

import java.util.List;

public interface ShopService {
    Shop addShop(RequestShopDto requestShopDto);

    List<Shop> getAllShop();

    String deleteShop(Long id);

    Shop updateShop(RequestShopDto requestShopDto);
}