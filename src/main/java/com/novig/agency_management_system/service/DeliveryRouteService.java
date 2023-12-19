package com.novig.agency_management_system.service;

import com.novig.agency_management_system.dto.requestDto.RequestDeliveryRouteDto;
import com.novig.agency_management_system.entity.DeliveryRoute;

import java.util.List;

public interface DeliveryRouteService {

    DeliveryRoute saveRoute(RequestDeliveryRouteDto requestDeliveryRouteDto);

    List<DeliveryRoute> getAllShop();

    String deleteRoute(Long id);

    DeliveryRoute updateShop(RequestDeliveryRouteDto requestDeliveryRouteDto);
}
