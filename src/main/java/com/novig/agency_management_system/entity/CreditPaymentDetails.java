package com.novig.agency_management_system.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreditPaymentDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "credit_id")
    private Long creditId;

    @ManyToOne
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    @Column(name = "credit_amount")
    private Double creditAmount;

    @Column(name = "paid_amount")
    private Double paidAmount;

    @Column(name = "last_payment_date")
    private LocalDate lastPaymentDate;
}
