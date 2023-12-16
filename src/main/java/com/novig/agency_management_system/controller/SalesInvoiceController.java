package com.novig.agency_management_system.controller;

import com.novig.agency_management_system.dto.requestDto.SalesInvoiceDTO;
import com.novig.agency_management_system.entity.SalesInvoice;
import com.novig.agency_management_system.service.SalesInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/sales-invoices")
public class SalesInvoiceController {

    @Autowired
    private SalesInvoiceService salesInvoiceService;

    @PostMapping("/save")
    public ResponseEntity<SalesInvoice> addSalesInvoice(@RequestBody SalesInvoiceDTO salesInvoiceDTO){

        SalesInvoice salesInvoice = salesInvoiceService.addSalesInvoice(salesInvoiceDTO);
        return ResponseEntity.ok(salesInvoice);
    }

//    @GetMapping("/get-by-id/{id}")
//    public ResponseEntity<SalesInvoice> getSalesInvoiceById(@PathVariable Long id) {
//        SalesInvoice salesInvoice = salesInvoiceService.getSalesInvoiceById(id);
//        return ResponseEntity.ok(salesInvoice);
//    }
}
