package com.novig.agency_management_system.controller;

import com.novig.agency_management_system.dto.requestDto.DateRangeRequestDto;
import com.novig.agency_management_system.dto.requestDto.RequestDailyTotalSalesDto;
import com.novig.agency_management_system.dto.requestDto.SalesInvoiceDTO;
import com.novig.agency_management_system.dto.responseDto.ResponseDailyTotalSalesDto;
import com.novig.agency_management_system.dto.responseDto.TotalSaleDetailsDTO;
import com.novig.agency_management_system.entity.SalesInvoice;
import com.novig.agency_management_system.entity.SalesInvoiceDetails;
import com.novig.agency_management_system.service.SalesInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/sales-invoices")
public class SalesInvoiceController {

    @Autowired
    private SalesInvoiceService salesInvoiceService;

    @PostMapping("/save")
    public ResponseEntity<String> createSale(@RequestBody SalesInvoiceDTO salesInvoiceDTO) {
        try {
            // Call the service method to handle the creation of the new sale
            salesInvoiceService.createNewSale(salesInvoiceDTO);

            return new ResponseEntity<>("Sale created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<String> deleteSale(@PathVariable Long id){
//        String res = salesInvoiceService.deleteSale(id);
//        return ResponseEntity.ok(res);
//
//    }
    @GetMapping("/getAllSales")
    public ResponseEntity<List<SalesInvoice>> getAllSales() {
        List<SalesInvoice> salesInvoiceList = salesInvoiceService.getAllSales();
        return ResponseEntity.ok(salesInvoiceList);
    }

    @GetMapping("/getAllSalesDetails")
    public ResponseEntity<List<SalesInvoiceDetails>> getAllSalesDetails() {
        List<SalesInvoiceDetails> salesInvoiceDetails = salesInvoiceService.getAllSalesDetails();
        return ResponseEntity.ok(salesInvoiceDetails);
    }

    @GetMapping("/getDailySalesTotal")
    public ResponseEntity<ResponseDailyTotalSalesDto> getDailyTotal(@RequestBody RequestDailyTotalSalesDto requestDailyTotalSalesDto) {
        ResponseDailyTotalSalesDto responseDailyTotalSalesDto = salesInvoiceService.getDailyTotal(requestDailyTotalSalesDto.getDate());
        return ResponseEntity.ok(responseDailyTotalSalesDto);
    }


    @GetMapping("/totalBySelectedDateRange")
    public ResponseEntity<TotalSaleDetailsDTO> getTotalSalesByDateRange(@RequestBody DateRangeRequestDto dateRangeRequestDto) {
        TotalSaleDetailsDTO totalSaleDetailsDateRange = salesInvoiceService.calTotalSaleDetailsByDateRange(dateRangeRequestDto);
        return ResponseEntity.ok(totalSaleDetailsDateRange);
    }

}
