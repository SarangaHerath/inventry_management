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
public class StockOut {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Stock_out_id")
    private Long stockOutId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "date_out", nullable = false)
    private LocalDate dateOut;
}
