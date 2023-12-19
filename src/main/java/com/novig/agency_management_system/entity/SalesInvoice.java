package com.novig.agency_management_system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "sale_invoice")
public class SalesInvoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_invoice_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @Column(name = "freeItems")
    private Double freeItems;

    @Column(name = "paymentMethod")
    private String paymentMethod;

    @Column(name = "discount")
    private Double discount;

    @OneToMany(mappedBy = "salesInvoice", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<SalesInvoiceDetails> salesInvoiceDetails;


}
