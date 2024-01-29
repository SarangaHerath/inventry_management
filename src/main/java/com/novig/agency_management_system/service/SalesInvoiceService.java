package com.novig.agency_management_system.service;

import com.novig.agency_management_system.dto.requestDto.AggDateRangeRequestDto;
import com.novig.agency_management_system.dto.requestDto.DateRangeRequestDto;
import com.novig.agency_management_system.dto.requestDto.SalesInvoiceDTO;
import com.novig.agency_management_system.dto.responseDto.ResponseAggDataDto;
import com.novig.agency_management_system.dto.responseDto.ResponseDailyTotalSalesDto;
import com.novig.agency_management_system.dto.responseDto.TotalSaleDetailsDTO;
import com.novig.agency_management_system.entity.SalesInvoice;
import com.novig.agency_management_system.entity.SalesInvoiceDetails;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;

public interface SalesInvoiceService {


    void createNewSale(SalesInvoiceDTO salesInvoiceDTO);

    List<SalesInvoice> getAllSales();

    List<SalesInvoiceDetails> getAllSalesDetails();

    ResponseDailyTotalSalesDto getDailyTotal(LocalDate date);

//    TotalSaleDetailsDTO calculateTotalSaleDetails(LocalDate selectedDate);

//    TotalSaleDetailsDTO calTotalSaleDetailsByDateRange(LocalDate startDate, LocalDate endDate);

    TotalSaleDetailsDTO calTotalSaleDetailsByDateRange( DateRangeRequestDto dateRangeRequest);

    ResponseAggDataDto getAggregatedData(AggDateRangeRequestDto aggDateRangeRequestDto);

//    TotalSaleDetailsDTO calTotalSaleDetailsByDateRange(LocalDate startDate, LocalDate endDate);

//    String deleteSale(Long id);
}
