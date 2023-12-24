package com.novig.agency_management_system.service.serviceImpl;
import org.springframework.dao.DataIntegrityViolationException;

import com.novig.agency_management_system.dto.requestDto.RequestShopDto;
import com.novig.agency_management_system.dto.responseDto.ShopDTO;
import com.novig.agency_management_system.entity.*;
import com.novig.agency_management_system.repository.*;
import com.novig.agency_management_system.service.ShopService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            DeliveryRoute deliveryRoute = deliveryRouteRepo.getById(requestShopDto.getDelivery_route_id());
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


//    @Override
//    @Transactional
//    public ResponseEntity<String> deleteShop(Long id) {
//        try {
//            Optional<Shop> shopOptional = shopRepo.findById(id);
//
//            if (shopOptional.isPresent()) {
//                Shop shop = shopOptional.get();
//
//                // Call the method to delete associated records
//                shop.deleteShopAndAssociatedRecords();
//
//                // Delete the shop entity
//                shopRepo.deleteById(id);
//
//                return ResponseEntity.ok("Shop with id " + id + " and associated records deleted successfully.");
//            } else {
//                return ResponseEntity.ok("Shop with id " + id + " not found.");
//            }
//        } catch (DataIntegrityViolationException ex) {
//            // The exception will be caught here for integrity constraint violations
//            return ResponseEntity.status(HttpStatus.OK).body("Cannot delete shop with id " + id + " due to existing references in the sale_invoice table.");
//        } catch (Exception e) {
//            // Handle other exceptions if necessary
//            return ResponseEntity.status(HttpStatus.OK).body("An error occurred while deleting shop with id " + id + ".");
//        }
//
//    }



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
    @Transactional
    public ResponseEntity<String> deleteShop(Long id) {
        try {
            Optional<Shop> shopOptional = shopRepo.findById(id);

            if (shopOptional.isPresent()) {
                Shop shop = shopOptional.get();

                // Call the method to delete associated records
                shop.deleteShopAndAssociatedRecords();

                // Delete the shop entity
                shopRepo.deleteById(id);

                return ResponseEntity.ok("Shop with id " + id + " and associated records deleted successfully.");
            } else {
                return ResponseEntity.ok("Shop with id " + id + " not found.");
            }
        } catch (DataIntegrityViolationException ex) {
            // The exception will be caught here for integrity constraint violations
            return ResponseEntity.ok("Cannot delete shop with id " + id + " due to existing references in the sale_invoice table.");
        } catch (Exception e) {
            // Handle other exceptions if necessary
            return ResponseEntity.ok("An error occurred while deleting shop with id " + id + ".");
        }
    }
}

