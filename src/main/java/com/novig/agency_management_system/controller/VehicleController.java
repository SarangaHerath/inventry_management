package com.novig.agency_management_system.controller;

import com.novig.agency_management_system.dto.requestDto.RequestVehicleDto;
import com.novig.agency_management_system.entity.Vehicle;
import com.novig.agency_management_system.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("**")
@RequestMapping("api/v1/vehicle")
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;

    @PostMapping("/add-vehicle")
    public ResponseEntity<Vehicle> addVehicle(@RequestBody RequestVehicleDto requestVehicleDto){

        Vehicle vehicle = vehicleService.addVehicle(requestVehicleDto);
        return ResponseEntity.ok(vehicle);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Vehicle>> getAllVehicle(){
        List<Vehicle> vehicleList = vehicleService.getAllVehicle();
        return ResponseEntity.ok(vehicleList);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteVehicle(@PathVariable Long id){
        String status = vehicleService.deleteVehicle(id);

        return ResponseEntity.ok(status);

    }
    @PutMapping("/update")
    public ResponseEntity<Vehicle> updateVehicle(@RequestBody RequestVehicleDto requestVehicleDto){
        Vehicle vehicle = vehicleService.updateVehicle(requestVehicleDto);
        return ResponseEntity.ok(vehicle);
    }
}
