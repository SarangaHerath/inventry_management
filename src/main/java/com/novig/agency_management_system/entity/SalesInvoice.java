package com.novig.agency_management_system.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "sale_invoice")
public class SalesInvoice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "sale_invoice_id")
    private Long saleInvoiceId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false,cascade = CascadeType.ALL)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    @Column(name = "shop_name", nullable = false)
    private String shopName;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "payment_type")
    private String paymentType;

}
