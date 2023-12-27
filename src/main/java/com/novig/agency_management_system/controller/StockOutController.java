package com.novig.agency_management_system.controller;

import com.novig.agency_management_system.dto.requestDto.RequestStockOutDto;
import com.novig.agency_management_system.entity.Product;
import com.novig.agency_management_system.entity.StockOut;
import com.novig.agency_management_system.service.StockOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("api/v1/stock-out")
public class StockOutController {
    @Autowired
    private StockOutService stockOutService;

    @PostMapping("/add-stockOut")
    public ResponseEntity<StockOut> addStockOut(@RequestBody RequestStockOutDto requestStockOutDto) {

        StockOut stockOut = stockOutService.addStockOut(requestStockOutDto);
        return ResponseEntity.ok(stockOut);
    }

    @GetMapping("/all")
    public ResponseEntity<List<StockOut>> getAllStockOut() {
        List<StockOut> stockOutList = stockOutService.getAllStockOut();
        return ResponseEntity.ok(stockOutList);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStockOut(@PathVariable Long id) {
        String status = stockOutService.deleteStockOut(id);

        return ResponseEntity.ok(status);

    }

    @PutMapping("/update")
    public ResponseEntity<StockOut> updateStockOut(@RequestBody RequestStockOutDto requestStockOutDto) {
        StockOut stockOut = stockOutService.updateStockOut(requestStockOutDto);
        return ResponseEntity.ok(stockOut);
    }
    @GetMapping("/getById/{id}")
    public ResponseEntity<StockOut> getByIdStockOut(@PathVariable Long id) {
        StockOut stockOut = stockOutService.getByIdStockOut(id);
        return ResponseEntity.ok(stockOut);
    }

    @GetMapping("/product-details/{productId}")
    public ResponseEntity<List<StockOut>> getOutOfStockProductDetails(@PathVariable Long productId) {
        List<StockOut> outOfStockDetails = stockOutService.getOutOfStockProductDetails(productId);
        return ResponseEntity.ok(outOfStockDetails);
    }
}
