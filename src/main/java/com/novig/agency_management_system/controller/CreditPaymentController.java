package com.novig.agency_management_system.controller;

import com.novig.agency_management_system.dto.requestDto.RequestChequeDateRangeDto;
import com.novig.agency_management_system.dto.requestDto.RequestCreditPaymentDto;
import com.novig.agency_management_system.entity.ChequeDetails;
import com.novig.agency_management_system.entity.CreditPaymentDetails;
import com.novig.agency_management_system.service.CreditPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1/credit")
public class CreditPaymentController {
    @Autowired
    private CreditPaymentService creditPaymentService;

    @PostMapping("/save")
    public ResponseEntity<CreditPaymentDetails> saveCreditPaymentDetails(@RequestBody RequestCreditPaymentDto requestCreditPaymentDto) {
        CreditPaymentDetails creditPaymentDetails = creditPaymentService.saveCreditPaymentDetails(requestCreditPaymentDto);

        return ResponseEntity.ok(creditPaymentDetails);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCreditPaymentDetails(@PathVariable Long id) {

        String res = creditPaymentService.deleteCreditPaymentDetails(id);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CreditPaymentDetails>> getAllCreditPayment() {
        List<CreditPaymentDetails> creditPaymentDetailsList = creditPaymentService.getAllCreditPayment();
        return ResponseEntity.ok(creditPaymentDetailsList);

    }

    @PutMapping("/update")
    public ResponseEntity<CreditPaymentDetails> updateCreditPayment(@RequestBody RequestCreditPaymentDto requestCreditPaymentDto) {

        CreditPaymentDetails creditPaymentDetails = creditPaymentService.updateCreditPayment(requestCreditPaymentDto);

        return ResponseEntity.ok(creditPaymentDetails);
    }
    @GetMapping("/getCreditByDateRange")
    public ResponseEntity<List<CreditPaymentDetails>> getCreditDetailsByDateRange(@RequestBody RequestChequeDateRangeDto requestChequeDateRangeDto){
        List<CreditPaymentDetails> creditPaymentDetailsList = creditPaymentService.getCreditDetailsByDateRange(requestChequeDateRangeDto);
        return ResponseEntity.ok(creditPaymentDetailsList);
    }
}
