package com.novig.agency_management_system.service.serviceImpl;

import com.novig.agency_management_system.dto.requestDto.ProductDto;
import com.novig.agency_management_system.dto.requestDto.SalesInvoiceDTO;
import com.novig.agency_management_system.dto.responseDto.ResponseDailyTotalSalesDto;
import com.novig.agency_management_system.entity.*;
import com.novig.agency_management_system.repository.DeliveryRouteRepo;
import com.novig.agency_management_system.repository.SalesInvoiceDetailsRepo;
import com.novig.agency_management_system.repository.SalesInvoiceRepo;
import com.novig.agency_management_system.repository.ShopRepo;
import com.novig.agency_management_system.service.SalesInvoiceService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SalesInvoiceServiceImpl implements SalesInvoiceService {

    @Autowired
    private SalesInvoiceRepo salesInvoiceRepo;

    @Autowired
    private SalesInvoiceDetailsRepo salesInvoiceDetailsRepo;

    @Autowired
    private ShopRepo shopRepo;

    @Autowired
    private DeliveryRouteRepo deliveryRouteRepo;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional // Enable transaction management
    public void createNewSale(SalesInvoiceDTO salesInvoiceDTO) {
        try {
            // Create a new SalesInvoice entity
            SalesInvoice salesInvoice = new SalesInvoice();

            // Retrieve the shop using the shop ID from the DTO
            Long deliverRouteId = salesInvoiceDTO.getDeliveryRouteId();
            DeliveryRoute deliveryroute = deliveryRouteRepo.findById(deliverRouteId)
                    .orElseThrow(() -> new IllegalArgumentException("Delivery Route not found with ID: " + deliverRouteId));

            // Retrieve the shop using the shop ID from the DTO
            Long shopId = salesInvoiceDTO.getShopId();
            Shop shop = shopRepo.findById(shopId)
                    .orElseThrow(() -> new IllegalArgumentException("Shop not found with ID: " + shopId));

            // Set the SalesInvoice properties
            salesInvoice.setShop(shop);
            salesInvoice.setDeliveryRoute(deliveryroute);
            salesInvoice.setDate(salesInvoiceDTO.getDate());
            salesInvoice.setReturnValue(salesInvoiceDTO.getReturnValue());
            salesInvoice.setTotal(salesInvoiceDTO.getTotal());
            salesInvoice.setFreeItems(salesInvoiceDTO.getFreeItems());
            salesInvoice.setCash(salesInvoiceDTO.getCash());
            salesInvoice.setCredit(salesInvoiceDTO.getCredit());
            salesInvoice.setCheque(salesInvoiceDTO.getCheque());
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
            salesInvoiceRepo.save(salesInvoice);
        } catch (Exception e) {
            // Log the exception for debugging purposes
            // logger.error("Error creating sale", e);
            throw new RuntimeException("Error creating sale: " + e.getMessage(), e);
        }
    }

    @Override
    public List<SalesInvoice> getAllSales() {

        List<SalesInvoice> salesInvoiceList = salesInvoiceRepo.findAll();
        return salesInvoiceList;
    }

    @Override
    public List<SalesInvoiceDetails> getAllSalesDetails() {
        List<SalesInvoiceDetails> salesInvoiceDetailsList = salesInvoiceDetailsRepo.findAll();
        return salesInvoiceDetailsList;
    }

    @Override
    public ResponseDailyTotalSalesDto getDailyTotal(LocalDate date) {

        LocalDate startDate = date;
        LocalDate endDate = date.plusDays(1);
        // Fetch sales invoices within the specified date range
        List<SalesInvoice> invoices = salesInvoiceRepo.findByDateBetween(startDate, endDate);

        // Calculate total values from fetched invoices
        Double cashTotal = 0.0;
        Double creditTotal = 0.0;
        Double chequeTotal = 0.0;
        Double total = 0.0;

        for (SalesInvoice invoice : invoices) {
            cashTotal += (invoice.getCash() != null) ? invoice.getCash() : 0.0;
            creditTotal += (invoice.getCredit() != null) ? invoice.getCredit() : 0.0;
            chequeTotal += (invoice.getCheque() != null) ? invoice.getCheque() : 0.0;
            total += (invoice.getTotal() != null) ? invoice.getTotal() : 0.0;
        }

        // Create and return the response DTO
        return new ResponseDailyTotalSalesDto(chequeTotal, creditTotal, cashTotal, total);
    }

    private Double parseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }


//    @Override
//    @Transactional
//    public String deleteSale(Long id) {
//        try {
//            SalesInvoice salesInvoice = salesInvoiceRepo.findById(id)
//                    .orElseThrow(() -> new IllegalArgumentException("Sale not found with ID: " + id));
//
//            // Set foreign key to null in SalesInvoiceDetails
//            if (salesInvoice.getSalesInvoiceDetails() != null) {
//                for (SalesInvoiceDetails details : salesInvoice.getSalesInvoiceDetails()) {
//                    details.setSalesInvoice(null);
//                }
//            }
//
//            salesInvoiceRepo.delete(salesInvoice);
//            return "Sale and associated SalesInvoiceDetails updated for Sale ID: " + id;
//        } catch (Exception e) {
//            // Log the exception for debugging purposes
//            // logger.error("Error deleting sale", e);
//            throw new RuntimeException("Error deleting sale: " + e.getMessage(), e);
//        }
//    }


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
