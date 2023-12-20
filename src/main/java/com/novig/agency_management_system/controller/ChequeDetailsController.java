package com.novig.agency_management_system.controller;

import com.novig.agency_management_system.dto.requestDto.RequestChequeDateRangeDto;
import com.novig.agency_management_system.dto.requestDto.RequestChequeDto;
import com.novig.agency_management_system.entity.ChequeDetails;
import com.novig.agency_management_system.service.ChequeDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1/cheque")
public class ChequeDetailsController {

    @Autowired
    private ChequeDetailsService chequeDetailsService;

    @PostMapping("/save")
    public ResponseEntity<ChequeDetails> saveChequeDetails(@RequestBody RequestChequeDto requestChequeDto) {
        ChequeDetails chequeDetails = chequeDetailsService.saveChequeDetails(requestChequeDto);

        return ResponseEntity.ok(chequeDetails);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteChequeDetails(@PathVariable Long id){

        String res = chequeDetailsService.deleteChequeDetails(id);
        return ResponseEntity.ok(res);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<ChequeDetails>> getAllCheque(){
        List<ChequeDetails> chequeDetailsList = chequeDetailsService.getAllCheque();
        return ResponseEntity.ok(chequeDetailsList);

    }
    @PutMapping("/update")
    public ResponseEntity<ChequeDetails> updateCheque(@RequestBody RequestChequeDto requestChequeDto){

        ChequeDetails chequeDetails = chequeDetailsService.updateCheque(requestChequeDto);

        return ResponseEntity.ok(chequeDetails);
    }
    @GetMapping("/getChequeByDateRange")
    public ResponseEntity<List<ChequeDetails>> getChequeDetailsByDateRange(@RequestBody RequestChequeDateRangeDto requestChequeDateRangeDto){
        List<ChequeDetails> chequeDetailsList = chequeDetailsService.getChequeDetailsByDateRange(requestChequeDateRangeDto);
        return ResponseEntity.ok(chequeDetailsList);
    }

}
