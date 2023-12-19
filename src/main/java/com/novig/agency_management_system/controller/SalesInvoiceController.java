package com.novig.agency_management_system.controller;

import com.novig.agency_management_system.dto.requestDto.SalesInvoiceDTO;
import com.novig.agency_management_system.service.SalesInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/sales-invoices")
public class SalesInvoiceController {

    @Autowired
    private SalesInvoiceService salesInvoiceService;

    @PostMapping("/new")
    public ResponseEntity<String> createSale(@RequestBody SalesInvoiceDTO salesInvoiceDTO) {
        try {
            // Call the service method to handle the creation of the new sale
            salesInvoiceService.createNewSale(salesInvoiceDTO);

            return new ResponseEntity<>("Sale created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
