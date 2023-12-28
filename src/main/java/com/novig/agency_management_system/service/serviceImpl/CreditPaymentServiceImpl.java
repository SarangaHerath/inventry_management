package com.novig.agency_management_system.service.serviceImpl;

import com.novig.agency_management_system.dto.requestDto.RequestChequeDateRangeDto;
import com.novig.agency_management_system.dto.requestDto.RequestCreditPaymentDto;
import com.novig.agency_management_system.entity.ChequeDetails;
import com.novig.agency_management_system.entity.CreditPaymentDetails;
import com.novig.agency_management_system.entity.SalesInvoice;
import com.novig.agency_management_system.entity.Shop;
import com.novig.agency_management_system.exception.CustomNotFoundException;
import com.novig.agency_management_system.exception.DatabaseOperationException;
import com.novig.agency_management_system.repository.CreditPaymentRepo;
import com.novig.agency_management_system.repository.SalesInvoiceRepo;
import com.novig.agency_management_system.repository.ShopRepo;
import com.novig.agency_management_system.service.CreditPaymentService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CreditPaymentServiceImpl implements CreditPaymentService {

    @Autowired
    private ShopRepo shopRepo;
    @Autowired
    private CreditPaymentRepo creditPaymentRepo;

    @Autowired
    private SalesInvoiceRepo salesInvoiceRepo;

    @Override
    public CreditPaymentDetails saveCreditPaymentDetails(RequestCreditPaymentDto requestCreditPaymentDto) {
        CreditPaymentDetails creditPaymentDetails = new CreditPaymentDetails();
        Shop shop = shopRepo.getById(requestCreditPaymentDto.getShop_id());

        creditPaymentDetails.setCreditAmount(requestCreditPaymentDto.getCreditAmount());
        creditPaymentDetails.setPaidAmount(requestCreditPaymentDto.getPaidAmount());
        creditPaymentDetails.setBillDate(requestCreditPaymentDto.getBillDate());
        creditPaymentDetails.setLastPaymentDate(requestCreditPaymentDto.getLastPaymentDate());
        creditPaymentDetails.setShop(shop);

        creditPaymentRepo.save(creditPaymentDetails);
        return creditPaymentDetails;
    }

    @Override
    public String deleteCreditPaymentDetails(Long id) {
        creditPaymentRepo.deleteById(id);
        return "Deleted!!";
    }

    @Override
    public List<CreditPaymentDetails> getAllCreditPayment() {
        try {
            List<CreditPaymentDetails> creditPaymentDetailsList = creditPaymentRepo.findAll();

            if (creditPaymentDetailsList.isEmpty()) {
                // Handle the case where no CreditPaymentDetails are found.
                // You can throw an exception or return an empty list based on your requirements.
                throw new CustomNotFoundException("No CreditPaymentDetails found");
            }

            return creditPaymentDetailsList;
        } catch (Exception e) {
            // Handle any other exceptions that might occur during the database operation.
            // You might want to log the exception or rethrow a custom exception.
            throw new DatabaseOperationException("Error fetching all CreditPaymentDetails", e);
        }
    }


    @Override
    public CreditPaymentDetails updateCreditPayment(RequestCreditPaymentDto requestCreditPaymentDto) {
        Optional<CreditPaymentDetails> optionalCreditPaymentDetails = creditPaymentRepo.findById(requestCreditPaymentDto.getCreditId());

        if (optionalCreditPaymentDetails.isPresent()) {
            CreditPaymentDetails creditPaymentDetails = optionalCreditPaymentDetails.get();
            creditPaymentDetails.setCreditAmount(requestCreditPaymentDto.getCreditAmount());
            creditPaymentDetails.setBillDate(requestCreditPaymentDto.getBillDate());
            creditPaymentDetails.setLastPaymentDate(requestCreditPaymentDto.getLastPaymentDate());
            creditPaymentDetails.setPaidAmount(requestCreditPaymentDto.getPaidAmount());
            creditPaymentRepo.save(creditPaymentDetails);
            return creditPaymentDetails;
        } else {
            // Handle the case where the entity is not found (e.g., throw an exception or return null)
            throw new EntityNotFoundException("CreditPaymentDetails not found with ID: " + requestCreditPaymentDto.getCreditId());
        }
    }

    @Override
    public List<CreditPaymentDetails> getCreditDetailsByDateRange(RequestChequeDateRangeDto requestChequeDateRangeDto) {
        List<CreditPaymentDetails> creditPaymentDetailsList = creditPaymentRepo.findByBillDateBetween(requestChequeDateRangeDto.getFromDate(), requestChequeDateRangeDto.getToDate());
        return creditPaymentDetailsList;
    }

    @Override
    public CreditPaymentDetails getCreditDetailsById(Long id) {

        try {
            Optional<CreditPaymentDetails> optionalCreditDetails = creditPaymentRepo.findById(id);

            if (optionalCreditDetails.isPresent()) {
                return optionalCreditDetails.get();
            } else {
                // Handle the case where the ChequeDetails with the given ID is not found.
                // You can throw an exception or return a default value based on your requirements.
                throw new CustomNotFoundException("ChequeDetails with ID " + id + " not found");
            }
        } catch (Exception e) {
            // Handle any other exceptions that might occur during the database operation.
            // You might want to log the exception or rethrow a custom exception.
            throw new DatabaseOperationException("Error fetching ChequeDetails with ID " + id, e);
        }
    }


    @Transactional
    public ResponseEntity<?> updateCreditPay(RequestCreditPaymentDto requestCreditPaymentDto) {
        try {
            // Retrieve CreditPaymentDetails by ID
            CreditPaymentDetails creditPaymentDetails = creditPaymentRepo.findById(requestCreditPaymentDto.getCreditId())
                    .orElseThrow(() -> new IllegalArgumentException("Credit Payment not found with ID: " + requestCreditPaymentDto.getCreditId()));

            // Check if credit amount is not negative after payment
            if (creditPaymentDetails.getCreditAmount() - requestCreditPaymentDto.getPaidAmount() < 0) {
                // Return a ResponseEntity with a custom error message and BAD_REQUEST status
                return ResponseEntity.badRequest().body("Credit amount cannot be negative after payment");
            }

            // Update credit amount and paid amount
            Double originalCreditAmount = creditPaymentDetails.getCreditAmount();
            Double originalPaidAmount = creditPaymentDetails.getPaidAmount();

            // Update CreditPaymentDetails
            creditPaymentDetails.setCreditAmount(originalCreditAmount - requestCreditPaymentDto.getPaidAmount());
            creditPaymentDetails.setPaidAmount(originalPaidAmount + requestCreditPaymentDto.getPaidAmount());
            creditPaymentDetails.setLastPaymentDate(requestCreditPaymentDto.getLastPaymentDate());

            // Save the updated CreditPaymentDetails and return the updated entity
            CreditPaymentDetails updatedCreditPaymentDetails = creditPaymentRepo.save(creditPaymentDetails);

            // Update SalesInvoice
            SalesInvoice salesInvoice = updatedCreditPaymentDetails.getSalesInvoice();
            if (salesInvoice != null) {
                // Update credit, cash, etc. based on the paid amount
                salesInvoice.setCredit(salesInvoice.getCredit() - requestCreditPaymentDto.getPaidAmount());
                salesInvoice.setCash(salesInvoice.getCash() + requestCreditPaymentDto.getPaidAmount());

                // Save the updated SalesInvoice
                salesInvoiceRepo.save(salesInvoice);
            }

            // Return the updated CreditPaymentDetails
            return ResponseEntity.ok(updatedCreditPaymentDetails);

        } catch (IllegalArgumentException e) {
            // Handle IllegalArgumentException (Credit Payment not found)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Credit Payment not found: " + e.getMessage());
        } catch (Exception e) {
            // Handle other exceptions and return a ResponseEntity with a custom error message and INTERNAL_SERVER_ERROR status
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating credit payment: " + e.getMessage());
        }
    }



}

