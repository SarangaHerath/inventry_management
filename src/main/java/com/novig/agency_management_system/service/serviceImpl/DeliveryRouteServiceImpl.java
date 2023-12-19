package com.novig.agency_management_system.service.serviceImpl;

import com.novig.agency_management_system.dto.requestDto.RequestDeliveryRouteDto;
import com.novig.agency_management_system.entity.DeliveryRoute;
import com.novig.agency_management_system.entity.Shop;
import com.novig.agency_management_system.repository.DeliveryRouteRepo;
import com.novig.agency_management_system.repository.ShopRepo;
import com.novig.agency_management_system.service.DeliveryRouteService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DeliveryRouteServiceImpl implements DeliveryRouteService {

    @Autowired
    private DeliveryRouteRepo deliveryRouteRepo;

    @Autowired
    private ShopRepo shopRepo;

    @Override
    public DeliveryRoute saveRoute(RequestDeliveryRouteDto requestDeliveryRouteDto) {
        DeliveryRoute deliveryRoute = new DeliveryRoute();
        deliveryRoute.setRouteName(requestDeliveryRouteDto.getRouteName());
        deliveryRouteRepo.save(deliveryRoute);
        return deliveryRoute;
    }

    @Override
    public List<DeliveryRoute> getAllShop() {
        List<DeliveryRoute> deliveryRouteList = deliveryRouteRepo.findAll();
        return deliveryRouteList;
    }

    @Override
    @Transactional
    public String deleteRoute(Long deliveryRouteId) {
        Optional<DeliveryRoute> deliveryRouteOptional = deliveryRouteRepo.findById(deliveryRouteId);

        if (deliveryRouteOptional.isPresent()) {
            DeliveryRoute deliveryRoute = deliveryRouteOptional.get();

            // Update associated shops to set deliveryRoute to null
            List<Shop> shops = shopRepo.findByDeliveryRoute(deliveryRoute);
            for (Shop shop : shops) {
                shop.setDeliveryRoute(null);
                shopRepo.save(shop);
            }

            // Delete the deliveryRoute
            deliveryRouteRepo.delete(deliveryRoute);

            return "DeliveryRoute with ID " + deliveryRouteId + " deleted successfully.";
        } else {
            // Handle the case when the deliveryRoute is not found
            return "DeliveryRoute not found with ID: " + deliveryRouteId;
        }
    }

    @Override
    public DeliveryRoute updateShop(RequestDeliveryRouteDto requestDeliveryRouteDto) {
        try {
            Optional<DeliveryRoute> deliveryRoute = deliveryRouteRepo.findById(requestDeliveryRouteDto.getId());

            if (deliveryRoute.isPresent()) {
                DeliveryRoute deliveryRoute1 = deliveryRoute.get();
                deliveryRoute1.setRouteName(requestDeliveryRouteDto.getRouteName());


                return deliveryRouteRepo.save(deliveryRoute1);
            } else {
                throw new EntityNotFoundException("Route not found with id: " + requestDeliveryRouteDto.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error updating route", e);
        }
    }

}
