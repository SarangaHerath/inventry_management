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
public class ChequeDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cheque_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    @Column(name = "cheque_number")
    private String chequeNumber;

    @Column(name = "received_date")
    private LocalDate receivedDate;

    @Column(name = "bank_date")
    private LocalDate bankDate;

    @Column(name = "amount")
    private Double amount;
}
