package com.novig.agency_management_system.service.serviceImpl;

import com.novig.agency_management_system.dto.requestDto.DateRangeRequestDto;
import com.novig.agency_management_system.dto.requestDto.ProductDto;
import com.novig.agency_management_system.dto.requestDto.RequestFreeIssueDto;
import com.novig.agency_management_system.dto.requestDto.SalesInvoiceDTO;
import com.novig.agency_management_system.dto.responseDto.ResponseDailyTotalSalesDto;

import com.novig.agency_management_system.dto.responseDto.TotalSaleDetailsDTO;
import com.novig.agency_management_system.entity.*;
import com.novig.agency_management_system.repository.SalesInvoiceDetailsRepo;
import com.novig.agency_management_system.repository.SalesInvoiceRepo;
import com.novig.agency_management_system.repository.ShopRepo;

import com.novig.agency_management_system.entity.*;
import com.novig.agency_management_system.repository.*;
import com.novig.agency_management_system.dto.responseDto.TotalSaleDetailsDTO;
import com.novig.agency_management_system.entity.*;
import com.novig.agency_management_system.repository.*;
import com.novig.agency_management_system.service.SalesInvoiceService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
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
    private StockOutRepo stockOutRepo;

    @Autowired
    private DeliveryRouteRepo deliveryRouteRepo;

    @Autowired
    private CreditPaymentRepo creditPaymentRepo;

    @Autowired
    private FreeIssueRepo freeIssueRepo;

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

                        updateStockQuantity(details.getProduct(), details.getQuantity());

                        return details;
                    })
                    .collect(Collectors.toList());


            // Set the SalesInvoiceDetails list to the SalesInvoice entity
            salesInvoice.setSalesInvoiceDetails(detailsList);

            // Create a list of FreeIsuue entities if applicable
            List<FreeIssue> freeIssueList = new ArrayList<>();

            List<RequestFreeIssueDto> requestFreeIssueDtos = salesInvoiceDTO.getRequestFreeIssueDtos();
            if (requestFreeIssueDtos != null && !requestFreeIssueDtos.isEmpty()) {
                freeIssueList = requestFreeIssueDtos.stream()
                        .map(detailsDto -> {
                            FreeIssue details = new FreeIssue();
                            details.setProduct(productDtoToEntity(detailsDto.getProduct()));
                            details.setQuantity(detailsDto.getQuantity());
                            details.setUnitPrice(detailsDto.getUnitPrice());
                            details.setSalesInvoice(salesInvoice);

                            updateStockQuantityFreeIsuue(details.getProduct(), details.getQuantity());

                            return details;
                        })
                        .collect(Collectors.toList());
            }


            // Set Data to credit table
            CreditPaymentDetails creditPaymentDetails = new CreditPaymentDetails();
            creditPaymentDetails.setBillDate(salesInvoiceDTO.getDate());
            creditPaymentDetails.setCreditAmount(salesInvoiceDTO.getCredit());
            creditPaymentDetails.setShop(shop);
            creditPaymentDetails.setPaidAmount(0.0);
            creditPaymentDetails.setSalesInvoice(salesInvoice);
            creditPaymentRepo.save(creditPaymentDetails);

            // Set the FreeIssue list to the FreeIsuue entity
            salesInvoice.setFreeIssueList(freeIssueList);

            // Save the FreeIssue entities
            freeIssueRepo.saveAll(freeIssueList);
            // Save the SalesInvoice entity, which will cascade to SalesInvoiceDetails due to CascadeType.ALL
            salesInvoiceRepo.save(salesInvoice);
        } catch (Exception e) {
            // Log the exception for debugging purposes
            // logger.error("Error creating sale", e);
            throw new RuntimeException("Error creating sale: " + e.getMessage(), e);
        }
    }

    private void updateStockQuantity(Product product, int soldQuantity) {
        try {
            // Retrieve the current stockOut for the product
            List<StockOut> stockOutList = stockOutRepo.findByProduct_ProductId(product.getProductId());

            if (!stockOutList.isEmpty()) {
                // Assuming there's only one StockOut entry for a product; handle appropriately if more are expected
                StockOut stockOut = stockOutList.get(0);

                // Update the stockOut quantity
                int currentQuantity = stockOut.getQuantity();
                if (currentQuantity >= soldQuantity) {
                    stockOut.setQuantity(currentQuantity - soldQuantity);
                    // Save the updated stockOut
                    stockOutRepo.save(stockOut);
                } else {
                    throw new RuntimeException("Not enough stock available for product: " + product.getProductName());
                }
            } else {
                throw new RuntimeException("Stock information not found for product: " + product.getProductName());
            }
        } catch (Exception e) {
            // Handle the exception or log the error
            throw new RuntimeException("Error updating stock quantity: " + e.getMessage(), e);
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

    @Override
    public TotalSaleDetailsDTO calTotalSaleDetailsByDateRange(DateRangeRequestDto dateRangeRequest) {

        LocalDate startDate = dateRangeRequest.getStartDate();
        LocalDate endDate = dateRangeRequest.getEndDate();
        long rowCount = salesInvoiceRepo.countByDateRange(startDate, endDate);
        Double totalSale = salesInvoiceRepo.getTotalSaleCountByDateRange(startDate, endDate);
        Double totalDiscount = salesInvoiceRepo.getTotalDiscountCountByDateRange(startDate, endDate);
        Double totalFreeItems = salesInvoiceRepo.getTotalFreeItemsCountByDateRange(startDate, endDate);
        Double totalReturnValues = salesInvoiceRepo.getTotalReturnValuesCountByDateRange(startDate, endDate);
        Double totalCheque = salesInvoiceRepo.getTotalChequeValuesCountByDateRange(startDate, endDate);
        Double totalCredit = creditPaymentRepo.getTotalCreditValuesCountByDateRange(startDate, endDate);
        Double totalCash =totalSale - (totalCheque+totalCredit);


        TotalSaleDetailsDTO totalSaleDetailsDTO = new TotalSaleDetailsDTO();
        totalSaleDetailsDTO.setRowCount(rowCount);
        totalSaleDetailsDTO.setTotalSale(totalSale);
        totalSaleDetailsDTO.setTotalDiscount(totalDiscount);
        totalSaleDetailsDTO.setTotalReturnValues(totalReturnValues);
        totalSaleDetailsDTO.setTotalFreeItems(totalFreeItems);
        totalSaleDetailsDTO.setTotalCheque(totalCheque);
        totalSaleDetailsDTO.setTotalCredit(totalCredit);
        totalSaleDetailsDTO.setTotalCash(totalCash);

        return totalSaleDetailsDTO;
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


    private void updateStockQuantityFreeIsuue(Product product, int soldQuantity) {
        try {
            // Retrieve all stockOut entries for the product
            List<StockOut> stockOutList = stockOutRepo.findByProduct_ProductId(product.getProductId());

            if (!stockOutList.isEmpty()) {
                // Iterate over each StockOut entry
                for (StockOut stockOut : stockOutList) {
                    // Update the stockOut quantity
                    int currentQuantity = stockOut.getQuantity();
                    if (currentQuantity >= soldQuantity) {
                        stockOut.setQuantity(currentQuantity - soldQuantity);
                        // Save the updated stockOut
                        stockOutRepo.save(stockOut);
                        return; // Exit the loop after updating one entry
                    }
                }

                // If loop completes without updating, throw an exception
                throw new RuntimeException("Not enough stock available for product: " + product.getProductName());
            } else {
                // If no StockOut entries found, throw an exception
                throw new RuntimeException("Stock information not found for product: " + product.getProductName());
            }
        } catch (Exception e) {
            // Handle the exception or log the error
            throw new RuntimeException("Error updating stock quantity: " + e.getMessage(), e);
        }
    }

}
