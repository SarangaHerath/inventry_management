package com.novig.agency_management_system.controller;

import com.novig.agency_management_system.dto.requestDto.RequestShopDto;
import com.novig.agency_management_system.dto.responseDto.ShopDTO;
import com.novig.agency_management_system.entity.Shop;
import com.novig.agency_management_system.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1/shop")
public class ShopController {
    @Autowired
    private ShopService shopService;

    @PostMapping("/add-shop")
    public ResponseEntity<Shop> addShop(@RequestBody RequestShopDto requestShopDto) {

        Shop shop = shopService.addShop(requestShopDto);
        return ResponseEntity.ok(shop);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ShopDTO>> getAllShop() {
        List<ShopDTO> shopList = shopService.getAllShop();
        return ResponseEntity.ok(shopList);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteShop(@PathVariable Long id) {
        ResponseEntity<String> response;
        try {
            ResponseEntity<String> deleteShopResponse = shopService.deleteShop(id);
            String status = deleteShopResponse.getBody();
            response = ResponseEntity.ok(status);
        } catch (DataIntegrityViolationException e) {
            // Handle foreign key constraint violation
            response = ResponseEntity.status(HttpStatus.OK).body("Cannot delete shop with id " + id + " due to existing references in the sale_invoice table.");
        } catch (Exception e) {
            // Handle other exceptions
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting shop with id " + id + ".");
        }
        return response;
    }


    @PutMapping("/update")
    public ResponseEntity<Shop> updateShop(@RequestBody RequestShopDto requestShopDto) {
        Shop shop = shopService.updateShop(requestShopDto);
        return ResponseEntity.ok(shop);
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<ShopDTO> getShopById(@PathVariable Long id) {
        ShopDTO shop = shopService.getShopById(id);
        return ResponseEntity.ok(shop);
    }
    @GetMapping("/by-delivery-route/{deliveryRouteId}")
    public ResponseEntity<List<ShopDTO>> getShopsByDeliveryRouteId(@PathVariable Long deliveryRouteId) {
        List<ShopDTO> shopList = shopService.getShopsByDeliveryRouteId(deliveryRouteId);
        return ResponseEntity.ok(shopList);
    }

}
