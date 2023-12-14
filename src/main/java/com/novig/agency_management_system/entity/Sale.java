package com.novig.agency_management_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "sale_id")
    private Long saleId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "discount_id", nullable = false)
    private Discount discount;

    @ManyToOne
    @JoinColumn(name = "free_issue_id", nullable = false)
    private FreeIssue freeIssue;

    @ManyToOne
    @JoinColumn(name = "return_id", nullable = false)
    private ReturnDetails returnDetails;

    @ManyToOne
    @JoinColumn(name = "payment_method_id", nullable = false)
    private PaymentMethod paymentMethod;

    @Column(name = "quantity",nullable = false)
    private int quantity;

    @Column(name = "unit_price",nullable = false)
    private Double unitPrice;
}
