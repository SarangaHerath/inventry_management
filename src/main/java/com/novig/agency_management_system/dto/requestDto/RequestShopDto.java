package com.novig.agency_management_system.dto.requestDto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RequestShopDto {

    private Long shopId;
    private String shopName;
    private String address;
    private String phoneNumber;

}
