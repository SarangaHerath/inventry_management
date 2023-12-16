package com.novig.agency_management_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "payment_method_id")
    private Long paymentMethodId;

    @ManyToOne
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    @Column(name = "payment_method", nullable = false)
    private String paymentType;

    @Column(name = "total_amount",nullable = false)
    private Double totalAmount;
}
