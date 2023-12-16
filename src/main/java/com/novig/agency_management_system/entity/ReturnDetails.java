package com.novig.agency_management_system.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReturnDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "return_details_id")
    private Long returnDetailsId;

    @ManyToOne
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    @Column(name = "return_amount")
    private Double returnAmount;
}
