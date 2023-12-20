package com.novig.agency_management_system.service.serviceImpl;

import com.novig.agency_management_system.dto.requestDto.RequestChequeDateRangeDto;
import com.novig.agency_management_system.dto.requestDto.RequestChequeDto;
import com.novig.agency_management_system.entity.ChequeDetails;
import com.novig.agency_management_system.entity.Shop;
import com.novig.agency_management_system.repository.ChequeDetailsRepo;
import com.novig.agency_management_system.repository.ShopRepo;
import com.novig.agency_management_system.service.ChequeDetailsService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChequeDetailsServiceImpl implements ChequeDetailsService {

    @Autowired
    private ShopRepo shopRepo;
    @Autowired
    private ChequeDetailsRepo chequeDetailsRepo;
    @Override
    public ChequeDetails saveChequeDetails(RequestChequeDto requestChequeDto) {
        ChequeDetails chequeDetails = new ChequeDetails();
        Shop shop = shopRepo.getById(requestChequeDto.getShop_id());

        chequeDetails.setChequeNumber(requestChequeDto.getChequeNumber());
        chequeDetails.setReceivedDate(requestChequeDto.getReceivedDate());
        chequeDetails.setBankDate(requestChequeDto.getBankDate());
        chequeDetails.setAmount(requestChequeDto.getAmount());
        chequeDetails.setShop(shop);

        chequeDetailsRepo.save(chequeDetails);
        return chequeDetails;
    }

    @Override
    public String deleteChequeDetails(Long id) {
        chequeDetailsRepo.deleteById(id);
        return "Deleted!!";
    }

    @Override
    public List<ChequeDetails> getAllCheque() {
        List<ChequeDetails> chequeDetailsList = chequeDetailsRepo.findAll();
        return chequeDetailsList;
    }

    @Override
    public ChequeDetails updateCheque(RequestChequeDto requestChequeDto) {
        Optional<ChequeDetails> optionalChequeDetails = chequeDetailsRepo.findById(requestChequeDto.getId());

        if (optionalChequeDetails.isPresent()) {
            ChequeDetails chequeDetails = optionalChequeDetails.get();
            chequeDetails.setChequeNumber(requestChequeDto.getChequeNumber());
            chequeDetails.setReceivedDate(requestChequeDto.getReceivedDate());
            chequeDetails.setBankDate(requestChequeDto.getBankDate());
            chequeDetails.setAmount(requestChequeDto.getAmount());
            chequeDetailsRepo.save(chequeDetails);
            return chequeDetails;
        } else {
            // Handle the case where the entity is not found (e.g., throw an exception or return null)
            throw new EntityNotFoundException("ChequeDetails not found with ID: " + requestChequeDto.getId());
        }
    }

    @Override
    public List<ChequeDetails> getChequeDetailsByDateRange(RequestChequeDateRangeDto requestChequeDateRangeDto) {

        List<ChequeDetails> chequeDetailsList = chequeDetailsRepo.findByReceivedDateBetween(requestChequeDateRangeDto.getFromDate(),requestChequeDateRangeDto.getToDate());
        return chequeDetailsList;
    }


}
