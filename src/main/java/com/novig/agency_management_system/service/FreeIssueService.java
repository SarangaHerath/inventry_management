package com.novig.agency_management_system.service;

import com.novig.agency_management_system.dto.requestDto.RequestMonthFreeDto;
import com.novig.agency_management_system.dto.responseDto.MonthlyFreeIssueResponseDto;
import com.novig.agency_management_system.entity.FreeIssue;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FreeIssueService {
    ResponseEntity<List<FreeIssue>> getAllFreeIssue() throws Exception;


    ResponseEntity<List<MonthlyFreeIssueResponseDto>> getAllFreeIssueMonth(RequestMonthFreeDto requestMonthFreeDto) throws Exception;
}
