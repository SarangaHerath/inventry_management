package com.novig.agency_management_system.dto.requestDto;

import com.novig.agency_management_system.entity.Shop;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestCreditPaymentDto {
    private Long creditId;
    private Long shop_id;
    private Double creditAmount;
    private Double paidAmount;
    private LocalDate lastPaymentDate;
}
