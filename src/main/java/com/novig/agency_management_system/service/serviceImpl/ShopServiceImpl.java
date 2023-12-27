package com.novig.agency_management_system.service.serviceImpl;

import org.springframework.dao.DataIntegrityViolationException;

import com.novig.agency_management_system.dto.requestDto.RequestShopDto;
import com.novig.agency_management_system.dto.responseDto.ShopDTO;
import com.novig.agency_management_system.entity.DeliveryRoute;
import com.novig.agency_management_system.entity.Shop;
import com.novig.agency_management_system.repository.ChequeDetailsRepo;
import com.novig.agency_management_system.repository.CreditPaymentRepo;
import com.novig.agency_management_system.repository.DeliveryRouteRepo;
import com.novig.agency_management_system.repository.SalesInvoiceRepo;
import com.novig.agency_management_system.repository.ShopRepo;
import com.novig.agency_management_system.service.ShopService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopRepo shopRepo;

    @Autowired
    private DeliveryRouteRepo deliveryRouteRepo;

    @Autowired
    private SalesInvoiceRepo salesInvoiceRepo;

    @Autowired
    private ChequeDetailsRepo chequeDetailsRepo;

    @Autowired
    private CreditPaymentRepo creditPaymentRepo;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Shop addShop(RequestShopDto requestShopDto) {
        try {
            DeliveryRoute deliveryRoute = deliveryRouteRepo.findById(requestShopDto.getDeliveryRouteId())
                    .orElseThrow(() -> new EntityNotFoundException("Delivery Route not found with id: " + requestShopDto.getDeliveryRouteId()));

            Shop shop = new Shop();
            shop.setShopId(requestShopDto.getShopId());
            shop.setDeliveryRoute(deliveryRoute);
            shop.setShopName(requestShopDto.getShopName());
            shop.setAddress(requestShopDto.getAddress());
            shop.setPhoneNumber(requestShopDto.getPhoneNumber());

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
    public Shop updateShop(RequestShopDto requestShopDto) {
        try {
            Assert.notNull(requestShopDto.getShopId(), "Shop ID must not be null");

            Optional<Shop> shopOptional = shopRepo.findById(requestShopDto.getShopId());


            Shop shop = shopOptional.orElseThrow(() -> new EntityNotFoundException("Shop not found with id: " + requestShopDto.getShopId()));

            DeliveryRoute deliveryRoute = deliveryRouteRepo.findById(requestShopDto.getDeliveryRouteId())
                    .orElseThrow(() -> new EntityNotFoundException("Delivery Route not found with id: " + requestShopDto.getDeliveryRouteId()));

            shop.setDeliveryRoute(deliveryRoute);
            shop.setShopName(requestShopDto.getShopName());
            shop.setAddress(requestShopDto.getAddress());
            shop.setPhoneNumber(requestShopDto.getPhoneNumber());

            return shopRepo.save(shop);
        } catch (Exception e) {
            throw new RuntimeException("Error updating shop", e);
        }
    }

    @Override
    public Shop getShopById(Long shopId) {
        try {
            return shopRepo.findById(shopId)
                    .orElseThrow(() -> new EntityNotFoundException("Shop not found with id: " + shopId));
        } catch (Exception e) {
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

    @Override
    @Transactional
    public ResponseEntity<String> deleteShop(Long id) {
        try {
            Assert.notNull(id, "Shop ID must not be null");

            Optional<Shop> shopOptional = shopRepo.findById(id);

            Shop shop = shopOptional.orElseThrow(() -> new EntityNotFoundException("Shop not found with id: " + id));

            shop.deleteShopAndAssociatedRecords();

            shopRepo.deleteById(id);

            return ResponseEntity.ok("Shop with id " + id + " and associated records deleted successfully.");
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.ok("Cannot delete shop with id " + id + " due to existing references in the sale_invoice table.");
        } catch (Exception e) {
            return ResponseEntity.ok("An error occurred while deleting shop with id " + id + ".");
        }
    }
}
