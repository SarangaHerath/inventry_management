package com.novig.agency_management_system.service.serviceImpl;

import com.novig.agency_management_system.dto.requestDto.ProductDto;
import com.novig.agency_management_system.dto.requestDto.SalesInvoiceDTO;
import com.novig.agency_management_system.dto.requestDto.SalesInvoiceDetailsDTO;
import com.novig.agency_management_system.entity.Product;
import com.novig.agency_management_system.entity.SalesInvoice;
import com.novig.agency_management_system.entity.SalesInvoiceDetails;
import com.novig.agency_management_system.entity.Shop;
import com.novig.agency_management_system.repository.SalesInvoiceDetailsRepo;
import com.novig.agency_management_system.repository.SalesInvoiceRepo;
import com.novig.agency_management_system.repository.ShopRepo;
import com.novig.agency_management_system.service.SalesInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SalesInvoiceServiceImpl implements SalesInvoiceService {

    @Autowired
    private SalesInvoiceRepo salesInvoiceRepository;

    @Autowired
    private SalesInvoiceDetailsRepo salesInvoiceDetailsRepository;

    @Autowired
    private ShopRepo shopRepo;

    @Override
    @Transactional // Enable transaction management
    public void createNewSale(SalesInvoiceDTO salesInvoiceDTO) {
        try {
            // Create a new SalesInvoice entity
            SalesInvoice salesInvoice = new SalesInvoice();

            // Retrieve the shop using the shop ID from the DTO
            Long shopId = salesInvoiceDTO.getShopId();
            Shop shop = shopRepo.findById(shopId)
                    .orElseThrow(() -> new IllegalArgumentException("Shop not found with ID: " + shopId));

            // Set the SalesInvoice properties
            salesInvoice.setShop(shop);
            salesInvoice.setFreeItems(salesInvoiceDTO.getFreeItems());
            salesInvoice.setPaymentMethod(salesInvoiceDTO.getPaymentMethod());
            salesInvoice.setDiscount(salesInvoiceDTO.getDiscount());

            // Create a list of SalesInvoiceDetails entities
            List<SalesInvoiceDetails> detailsList = salesInvoiceDTO.getSalesInvoiceDetails().stream()
                    .map(detailsDto -> {
                        SalesInvoiceDetails details = new SalesInvoiceDetails();
                        details.setProduct(productDtoToEntity(detailsDto.getProduct()));
                        details.setQuantity(detailsDto.getQuantity());
                        details.setUnitPrice(detailsDto.getUnitPrice());
                        details.setSalesInvoice(salesInvoice);
                        return details;
                    })
                    .collect(Collectors.toList());

            // Set the SalesInvoiceDetails list to the SalesInvoice entity
            salesInvoice.setSalesInvoiceDetails(detailsList);

            // Save the SalesInvoice entity, which will cascade to SalesInvoiceDetails due to CascadeType.ALL
            salesInvoiceRepository.save(salesInvoice);
        } catch (Exception e) {
            // Log the exception for debugging purposes
            // logger.error("Error creating sale", e);
            throw new RuntimeException("Error creating sale: " + e.getMessage(), e);
        }
    }

    private Product productDtoToEntity(ProductDto productDto) {
        Product product = new Product();
        product.setProductId(productDto.getProductId());
        product.setProductName(productDto.getProductName());
        product.setQuantity(productDto.getQuantity());
        product.setUnitPrice(productDto.getUnitPrice());
        // Set other properties as needed
        return product;
    }
}
