package com.novig.agency_management_system.service;

import com.novig.agency_management_system.dto.requestDto.RequestChequeDateRangeDto;
import com.novig.agency_management_system.dto.requestDto.RequestCreditPaymentDto;
import com.novig.agency_management_system.entity.CreditPaymentDetails;

import java.util.List;

public interface CreditPaymentService {
    CreditPaymentDetails saveCreditPaymentDetails(RequestCreditPaymentDto requestCreditPaymentDto);

    String deleteCreditPaymentDetails(Long id);

    List<CreditPaymentDetails> getAllCreditPayment();

    CreditPaymentDetails updateCreditPayment(RequestCreditPaymentDto requestCreditPaymentDto);

    List<CreditPaymentDetails> getCreditDetailsByDateRange(RequestChequeDateRangeDto requestChequeDateRangeDto);
}
