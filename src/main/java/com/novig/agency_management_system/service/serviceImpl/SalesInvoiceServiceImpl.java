package com.novig.agency_management_system.service.serviceImpl;

import com.novig.agency_management_system.dto.requestDto.SalesInvoiceDTO;
import com.novig.agency_management_system.entity.SalesInvoice;
import com.novig.agency_management_system.entity.SalesInvoiceDetails;
import com.novig.agency_management_system.entity.Shop;
import com.novig.agency_management_system.repository.SalesInvoiceDetailsRepo;
import com.novig.agency_management_system.repository.SalesInvoiceRepo;
import com.novig.agency_management_system.repository.ShopRepo;
import com.novig.agency_management_system.service.SalesInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void createNewSale(SalesInvoiceDTO salesInvoiceDTO) {

    }

  
}
