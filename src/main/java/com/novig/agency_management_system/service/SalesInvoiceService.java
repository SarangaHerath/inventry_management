package com.novig.agency_management_system.service;

import com.novig.agency_management_system.dto.requestDto.SalesInvoiceDTO;
import com.novig.agency_management_system.dto.responseDto.ResponseDailyTotalSalesDto;
import com.novig.agency_management_system.entity.SalesInvoice;
import com.novig.agency_management_system.entity.SalesInvoiceDetails;

import java.time.LocalDate;
import java.util.List;

public interface SalesInvoiceService {


    void createNewSale(SalesInvoiceDTO salesInvoiceDTO);

    List<SalesInvoice> getAllSales();

    List<SalesInvoiceDetails> getAllSalesDetails();

    ResponseDailyTotalSalesDto getDailyTotal(LocalDate date);

//    String deleteSale(Long id);
}
