package com.novig.agency_management_system.controller;

import com.novig.agency_management_system.dto.requestDto.RequestProductDto;
import com.novig.agency_management_system.entity.Product;
import com.novig.agency_management_system.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin()
@RequestMapping("api/v1/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/add-product")
    public ResponseEntity<Product> addProduct(@RequestBody RequestProductDto requestProductDto) {

        Product product = productService.addProduct(requestProductDto);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProduct() {
        List<Product> productList = productService.getAllProduct();
        return ResponseEntity.ok(productList);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        String status = productService.deleteProduct(id);

        return ResponseEntity.ok(status);

    }

    @PutMapping("/update")
    public ResponseEntity<Product> updateProduct(@RequestBody RequestProductDto requestProductDto) {
        Product product = productService.updateProduct(requestProductDto);
        return ResponseEntity.ok(product);
    }
    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> optionalProduct = productService.getProductById(id);

        return optionalProduct
                .map(Product -> ResponseEntity.ok(Product))
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/by-category/{categoryId}")
    public ResponseEntity<List<Product>> getProductsByCategoryId(@PathVariable Long categoryId) {
        List<Product> productList = productService.getProductsByCategoryId(categoryId);
        return ResponseEntity.ok(productList);
    }
}
