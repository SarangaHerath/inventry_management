package com.novig.agency_management_system.service;

import com.novig.agency_management_system.dto.requestDto.RequestProductDto;
import com.novig.agency_management_system.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product addProduct(RequestProductDto requestProductDto);

    List<Product> getAllProduct();

    String deleteProduct(Long id);

    Product updateProduct(RequestProductDto requestProductDto);
    Optional<Product> getProductById(Long id);
}
