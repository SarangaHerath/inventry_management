package com.novig.agency_management_system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.novig.agency_management_system.dto.requestDto.RequestDeliveryRouteDto;
import com.novig.agency_management_system.dto.responseDto.ShopDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_id")
    private Long shopId;

    @JsonIgnore
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "delivery_route_id", nullable = true)
    private DeliveryRoute deliveryRoute;


    @Column(name = "shop_name", nullable = false)
    private String shopName;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @JsonIgnore
    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CreditPaymentDetails> creditPaymentDetailsList;

    @JsonIgnore
    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChequeDetails> chequeDetailsList;

    public ShopDTO toDTO() {
        ShopDTO dto = new ShopDTO();
        dto.setShopId(this.shopId);
        dto.setShopName(this.shopName);
        dto.setAddress(this.address);
        dto.setPhoneNumber(this.phoneNumber);

        // Set deliveryRouteDetails using deliveryRoute details
        if (this.deliveryRoute != null) {
            dto.setDeliveryRoute(convertDeliveryRouteToDto(this.deliveryRoute));
        }

        return dto;
    }

    private RequestDeliveryRouteDto convertDeliveryRouteToDto(DeliveryRoute deliveryRoute) {
        RequestDeliveryRouteDto routeDto = new RequestDeliveryRouteDto();
        routeDto.setId(deliveryRoute.getId());
        routeDto.setRouteName(deliveryRoute.getRouteName());
        // Set other properties as needed in RequestDeliveryRouteDto
        return routeDto;
    }

    // Add a method to delete the shop and its associated records
    public void deleteShopAndAssociatedRecords() {
        if (creditPaymentDetailsList != null) {
            creditPaymentDetailsList.clear();
        }

        if (chequeDetailsList != null) {
            chequeDetailsList.clear();
        }

        // Set deliveryRoute to null to avoid foreign key constraints
        this.deliveryRoute = null;
    }
}
