package com.novig.agency_management_system.controller;

import com.novig.agency_management_system.dto.requestDto.RequestDeliveryRouteDto;
import com.novig.agency_management_system.entity.DeliveryRoute;
import com.novig.agency_management_system.service.DeliveryRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1/route")
public class DeliveryRouteController {

    @Autowired
    private DeliveryRouteService deliveryRouteService;


    @PostMapping("/save")
    public DeliveryRoute saveRoute(@RequestBody RequestDeliveryRouteDto requestDeliveryRouteDto) {

        DeliveryRoute deliveryRoute = deliveryRouteService.saveRoute(requestDeliveryRouteDto);
        return deliveryRoute;
    }

    @GetMapping("/all")
    public ResponseEntity<List<DeliveryRoute>> getAllRoute() {
        List<DeliveryRoute> deliveryRouteList = deliveryRouteService.getAllShop();
        return ResponseEntity.ok(deliveryRouteList);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRoute(@PathVariable Long id) {
        String status = deliveryRouteService.deleteRoute(id);
        return ResponseEntity.ok(status);
    }

    @PutMapping("/update")
    public ResponseEntity<DeliveryRoute> updateRoute(@RequestBody RequestDeliveryRouteDto requestDeliveryRouteDto) {
        DeliveryRoute deliveryRoute1 = deliveryRouteService.updateRoute(requestDeliveryRouteDto);
        return ResponseEntity.ok(deliveryRoute1);
    }
    @GetMapping("/getById/{id}")
    public ResponseEntity<DeliveryRoute> getRouteById(@PathVariable Long id){
        DeliveryRoute deliveryRoute = deliveryRouteService.getRouteById(id);
       return ResponseEntity.ok(deliveryRoute);
    }

}
