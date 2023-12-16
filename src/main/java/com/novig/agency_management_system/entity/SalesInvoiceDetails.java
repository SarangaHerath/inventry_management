package com.novig.agency_management_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "sale_invoice_details")
public class SalesInvoiceDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "sale_invoice_details_id")
    private Long saleInvoiceDetailsId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false,cascade = CascadeType.ALL)
    @JoinColumn(name = "sale_invoice_id", nullable = false)
    private SalesInvoice salesInvoiceId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false,cascade = CascadeType.ALL)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    @ManyToOne(fetch = FetchType.LAZY, optional = false,cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "product_name",nullable = false)
    private String productName;

    @Column(name = "quantity",nullable = false)
    private int quantity;

    @Column(name = "unit_price",nullable = false)
    private Double unitPrice;

}
