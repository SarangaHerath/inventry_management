package com.novig.agency_management_system.service.serviceImpl;

import com.novig.agency_management_system.dto.requestDto.RequestChequeDateRangeDto;
import com.novig.agency_management_system.dto.requestDto.RequestCreditPaymentDto;
import com.novig.agency_management_system.entity.ChequeDetails;
import com.novig.agency_management_system.entity.CreditPaymentDetails;
import com.novig.agency_management_system.entity.Shop;
import com.novig.agency_management_system.exception.CustomNotFoundException;
import com.novig.agency_management_system.exception.DatabaseOperationException;
import com.novig.agency_management_system.repository.CreditPaymentRepo;
import com.novig.agency_management_system.repository.ShopRepo;
import com.novig.agency_management_system.service.CreditPaymentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CreditPaymentServiceImpl implements CreditPaymentService {

    @Autowired
    private ShopRepo shopRepo;
    @Autowired
    private CreditPaymentRepo creditPaymentRepo;

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
        List<CreditPaymentDetails> creditPaymentDetailsList = creditPaymentRepo.findAll();
        return creditPaymentDetailsList;
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
}

