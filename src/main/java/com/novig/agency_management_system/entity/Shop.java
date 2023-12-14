package com.novig.agency_management_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "shop_id")
    private Long shopId;

    @Column(name = "shop_name",nullable = false)
    private String shopName;

    @Column(name = "address",nullable = false)
    private String address;

    @Column(name = "phone_number",nullable = false)
    private String phoneNumber;


}
