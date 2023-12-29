package com.novig.agency_management_system.service.serviceImpl;
import com.novig.agency_management_system.dto.requestDto.RequestVehicleDto;
import com.novig.agency_management_system.entity.Vehicle;
import com.novig.agency_management_system.repository.VehicleRepo;
import com.novig.agency_management_system.service.VehicleService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepo vehicleRepo;

    @Override
    public Vehicle addVehicle(RequestVehicleDto requestVehicleDto) {
        try {
            Vehicle vehicle = new Vehicle(
                    requestVehicleDto.getVehicleId(),
                    requestVehicleDto.getOwnerName(),
                    requestVehicleDto.getVehicleNumber()

            );
            vehicleRepo.save(vehicle);
            return vehicle;
        } catch (Exception e) {

            throw new RuntimeException("Error saving vehicle: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Vehicle> getAllVehicle() {
        List<Vehicle> vehicleList = vehicleRepo.findAll();
        return vehicleList;
    }

    @Override
    public String deleteVehicle(Long id) {
        vehicleRepo.deleteById(id);
        return "Deleted !!";
    }

    @Override
    public Vehicle updateVehicle(RequestVehicleDto requestVehicleDto) {
        try {
            Optional<Vehicle> vehicleOptional = vehicleRepo.findById(requestVehicleDto.getVehicleId());

            if (vehicleOptional.isPresent()) {
                Vehicle vehicle = vehicleOptional.get();
                vehicle.setOwnerName(requestVehicleDto.getOwnerName());
                vehicle.setVehicleNumber(requestVehicleDto.getVehicleNumber());

                return vehicleRepo.save(vehicle);
            } else {
                throw new EntityNotFoundException("Vehicle not found with id: " + requestVehicleDto.getVehicleId());
            }
        } catch (Exception e) {

            e.printStackTrace();
            throw new RuntimeException("Error updating vehicle", e);
        }
    }

    @Override
    public Vehicle getVehicleById(Long vehicleId) {
        try {
            return vehicleRepo.findById(vehicleId)
                    .orElseThrow(() -> new EntityNotFoundException("Vehicle not found with id: " + vehicleId));
        } catch (Exception e) {

            e.printStackTrace();
            throw new RuntimeException("Error getting vehicle by id", e);
        }
    }
}

