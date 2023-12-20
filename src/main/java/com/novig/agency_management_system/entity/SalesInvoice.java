package com.novig.agency_management_system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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

    @Column(name = "bill_date")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @Column(name = "return_value")
    private Double returnValue;

    @Column(name = "freeItems")
    private Double freeItems;

    @Column(name = "paymentMethod")
    private String paymentMethod;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "total")
    private Double total;

    @OneToMany(mappedBy = "salesInvoice", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<SalesInvoiceDetails> salesInvoiceDetails;


}
