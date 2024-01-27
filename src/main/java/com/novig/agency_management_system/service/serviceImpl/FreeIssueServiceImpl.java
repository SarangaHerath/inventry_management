package com.novig.agency_management_system.service.serviceImpl;

import com.novig.agency_management_system.dto.requestDto.RequestMonthFreeDto;
import com.novig.agency_management_system.dto.responseDto.MonthlyFreeIssueResponseDto;
import com.novig.agency_management_system.entity.FreeIssue;
import com.novig.agency_management_system.repository.FreeIssueRepo;
import com.novig.agency_management_system.service.FreeIssueService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FreeIssueServiceImpl implements FreeIssueService {

    private final FreeIssueRepo freeIssueRepo;

    public FreeIssueServiceImpl(FreeIssueRepo freeIssueRepo) {
        this.freeIssueRepo = freeIssueRepo;
    }

    @Override
    public ResponseEntity<List<FreeIssue>> getAllFreeIssue() throws Exception {
        try {
            List<FreeIssue> freeIssueList = freeIssueRepo.findAll();
            return ResponseEntity.ok(freeIssueList);
        }catch (Exception e){
            throw new Exception("Error Occur during get all freeIssue");
        }
    }

    @Override
    public ResponseEntity<List<MonthlyFreeIssueResponseDto>> getAllFreeIssueMonth(RequestMonthFreeDto requestMonthFreeDto) throws Exception {
        try {
            List<FreeIssue> freeIssueList = freeIssueRepo.findFreeIssuesWithFreeItemsBetweenDates(requestMonthFreeDto.getFromDate(),requestMonthFreeDto.getToDate());
            List<MonthlyFreeIssueResponseDto> responseDtoList = freeIssueList.stream()
                    .collect(Collectors.groupingBy(
                            freeIssue -> freeIssue.getProduct().getProductName(),
                            Collectors.summingInt(FreeIssue::getQuantity)))
                    .entrySet()
                    .stream()
                    .map(entry -> new MonthlyFreeIssueResponseDto(entry.getKey(), entry.getValue()))
                    .collect(Collectors.toList());
             return ResponseEntity.ok(responseDtoList);
        }catch (Exception e){
               throw new Exception("Error Occur during get month freeIssue");
        }
    }
}
