package com.novig.agency_management_system.service;

import com.novig.agency_management_system.dto.requestDto.SalesInvoiceDTO;
import com.novig.agency_management_system.entity.SalesInvoice;

import java.util.Optional;

public interface SalesInvoiceService {


    void createNewSale(SalesInvoiceDTO salesInvoiceDTO);
}
