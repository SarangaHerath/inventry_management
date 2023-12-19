package com.novig.agency_management_system.service;

import com.novig.agency_management_system.dto.requestDto.RequestVehicleDto;
import com.novig.agency_management_system.entity.Vehicle;

import java.util.List;

public interface VehicleService {
    Vehicle addVehicle(RequestVehicleDto requestVehicleDto);

    List<Vehicle> getAllVehicle();

    String deleteVehicle(Long id);

    Vehicle updateVehicle(RequestVehicleDto requestVehicleDto);

    Vehicle getVehicleById(Long vehicleId);
}
