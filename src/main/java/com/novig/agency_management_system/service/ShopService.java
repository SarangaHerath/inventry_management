package com.novig.agency_management_system.service;

import com.novig.agency_management_system.dto.requestDto.RequestShopDto;
import com.novig.agency_management_system.dto.responseDto.ShopDTO;
import com.novig.agency_management_system.entity.Shop;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ShopService {
    Shop addShop(RequestShopDto requestShopDto);

    List<ShopDTO> getAllShop();



    Shop updateShop(RequestShopDto requestShopDto);

    Shop getShopById(Long shopId);


    List<ShopDTO> getShopsByDeliveryRouteId(Long deliveryRouteId);

    ResponseEntity<String> deleteShop(Long id);
}
