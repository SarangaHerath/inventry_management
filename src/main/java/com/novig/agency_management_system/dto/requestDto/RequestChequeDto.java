package com.novig.agency_management_system.dto.requestDto;

import com.novig.agency_management_system.entity.Shop;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestChequeDto {
    private Long id;
    private Long shop_id;
    private String chequeNumber;
    private LocalDate receivedDate;
    private LocalDate bankDate;
    private Double amount;
}

