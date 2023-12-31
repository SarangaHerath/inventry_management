package com.novig.agency_management_system.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RequestVehicleDto {

    private Long vehicleId;
    private String ownerName;
    private String vehicleNumber;
}
