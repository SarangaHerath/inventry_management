package com.novig.agency_management_system.controller;

import com.novig.agency_management_system.dto.requestDto.RequestMonthFreeDto;
import com.novig.agency_management_system.dto.responseDto.MonthlyFreeIssueResponseDto;
import com.novig.agency_management_system.entity.FreeIssue;
import com.novig.agency_management_system.service.FreeIssueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1/freeIssue")
public class FreeIssueController {

    private final FreeIssueService freeIssueService;

    public FreeIssueController(FreeIssueService freeIssueService) {
        this.freeIssueService = freeIssueService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<FreeIssue>> getAllFreeIssue() throws Exception {
        return freeIssueService.getAllFreeIssue();
    }

    @PostMapping("/getFreeIssueMonth")
    public ResponseEntity<List<MonthlyFreeIssueResponseDto>> getAllFreeIssueMonth(@RequestBody RequestMonthFreeDto requestMonthFreeDto) throws Exception {
        return freeIssueService.getAllFreeIssueMonth(requestMonthFreeDto);
    }
}
