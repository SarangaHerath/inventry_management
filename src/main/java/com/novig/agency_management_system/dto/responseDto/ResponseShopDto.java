package com.novig.agency_management_system.dto.responseDto;

import com.novig.agency_management_system.entity.DeliveryRoute;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseShopDto {
    private Long shopId;
    private DeliveryRoute deliveryRoute;
    private String shopName;
    private String address;
    private String phoneNumber;
}
