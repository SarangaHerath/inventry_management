package com.novig.agency_management_system.service.serviceImpl;


import com.novig.agency_management_system.dto.requestDto.RequestShopDto;
import com.novig.agency_management_system.dto.responseDto.ShopDTO;
import com.novig.agency_management_system.entity.DeliveryRoute;
import com.novig.agency_management_system.entity.Shop;
import com.novig.agency_management_system.repository.DeliveryRouteRepo;
import com.novig.agency_management_system.repository.ShopRepo;
import com.novig.agency_management_system.service.ShopService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopRepo shopRepo;

    @Autowired
    private DeliveryRouteRepo deliveryRouteRepo;


    @Override
    public Shop addShop(RequestShopDto requestShopDto) {
        try {
            DeliveryRoute deliveryRoute = deliveryRouteRepo.getById(requestShopDto.getDelivery_route_id());
            Shop shop = new Shop(
                    requestShopDto.getShopId(),
                    deliveryRoute,
                    requestShopDto.getShopName(),
                    requestShopDto.getAddress(),
                    requestShopDto.getPhoneNumber()
            );
            shopRepo.save(shop);
            return shop;
        } catch (Exception e) {

            throw new RuntimeException("Error saving shop: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ShopDTO> getAllShop() {
        List<Shop> shopList = shopRepo.findAll();
        return shopList.stream()
                .map(Shop::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public String deleteShop(Long id) {
        shopRepo.deleteById(id);
        return "Deleted !!";
    }

    @Override
    public Shop updateShop(RequestShopDto requestShopDto) {
        try {
            Optional<Shop> shopOptional = shopRepo.findById(requestShopDto.getShopId());

            if (shopOptional.isPresent()) {
                Shop shop = shopOptional.get();
                shop.setShopName(requestShopDto.getShopName());
                shop.setAddress(requestShopDto.getAddress());
                shop.setPhoneNumber(requestShopDto.getPhoneNumber());


                return shopRepo.save(shop);
            } else {
                throw new EntityNotFoundException("Shop not found with id: " + requestShopDto.getShopId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error updating shop", e);
        }
    }

    @Override
    public Shop getShopById(Long shopId) {
        try {
            return shopRepo.findById(shopId)
                    .orElseThrow(() -> new EntityNotFoundException("Shop not found with id: " + shopId));
        } catch (Exception e) {
            // Log the exception or handle it based on your application's requirements.
            e.printStackTrace();
            throw new RuntimeException("Error getting shop by id", e);
        }
    }
    @Override
    public List<ShopDTO> getShopsByDeliveryRouteId(Long deliveryRouteId) {
        List<Shop> shopList = shopRepo.findByDeliveryRouteId(deliveryRouteId);
        return shopList.stream()
                .map(Shop::toDTO)
                .collect(Collectors.toList());
    }

}

