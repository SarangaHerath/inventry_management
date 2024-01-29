package com.novig.agency_management_system.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "weight", nullable = false)
    private String weight;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "unit_price", nullable = false)
    private Double unitPrice;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "category_id")
    @JsonIgnoreProperties("products")
    private Category category;
}
