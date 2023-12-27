package com.novig.agency_management_system.service;

import com.novig.agency_management_system.dto.requestDto.RequestChequeDateRangeDto;
import com.novig.agency_management_system.dto.requestDto.RequestChequeDto;
import com.novig.agency_management_system.entity.ChequeDetails;

import java.util.List;

public interface ChequeDetailsService {
    ChequeDetails saveChequeDetails(RequestChequeDto requestChequeDto);

    String deleteChequeDetails(Long id);

    List<ChequeDetails> getAllCheque();

    ChequeDetails updateCheque(RequestChequeDto requestChequeDto);

    List<ChequeDetails> getChequeDetailsByDateRange(RequestChequeDateRangeDto requestChequeDateRangeDto);

    ChequeDetails getChequeById(Long id);
}
