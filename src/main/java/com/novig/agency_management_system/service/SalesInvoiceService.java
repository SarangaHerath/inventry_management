package com.novig.agency_management_system.service;

import com.novig.agency_management_system.dto.requestDto.SalesInvoiceDTO;

public interface SalesInvoiceService {


    void createNewSale(SalesInvoiceDTO salesInvoiceDTO);
}
