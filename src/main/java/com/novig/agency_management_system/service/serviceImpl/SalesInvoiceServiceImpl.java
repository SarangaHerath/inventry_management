package com.novig.agency_management_system.service.serviceImpl;

import com.novig.agency_management_system.dto.requestDto.SalesInvoiceDTO;
import com.novig.agency_management_system.entity.SalesInvoice;
import com.novig.agency_management_system.entity.Shop;
import com.novig.agency_management_system.repository.SalesInvoiceDetailsRepo;
import com.novig.agency_management_system.repository.SalesInvoiceRepo;
import com.novig.agency_management_system.repository.ShopRepo;
import com.novig.agency_management_system.service.SalesInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SalesInvoiceServiceImpl implements SalesInvoiceService {

    @Autowired
    private SalesInvoiceRepo salesInvoiceRepository;

    @Autowired
    private SalesInvoiceDetailsRepo salesInvoiceDetailsRepository;

    @Autowired
    private ShopRepo shopRepo;


    @Override
    public SalesInvoice addSalesInvoice(SalesInvoiceDTO salesInvoiceDTO) {
        // Convert DTO to Entity
        SalesInvoice salesInvoice = new SalesInvoice();
        salesInvoice.setSaleInvoiceId(salesInvoiceDTO.getSaleInvoiceId());
        // Assuming salesInvoiceDTO.getShopId() returns a Long ID
        Long shopId = salesInvoiceDTO.getShopId();
        Shop shop = shopRepo.findById(shopId).orElse(null);
        salesInvoice.setShop(shop);
        salesInvoice.setShopName(salesInvoiceDTO.getShopName());
        salesInvoice.setTotalAmount(salesInvoiceDTO.getTotalAmount());
        salesInvoice.setDiscount(salesInvoiceDTO.getDiscount());
        salesInvoice.setPaymentType(salesInvoiceDTO.getPaymentType());

        // Save the entity to the database
        SalesInvoice savedInvoice = salesInvoiceRepository.save(salesInvoice);

        return savedInvoice;
    }

//    @Override
//    public SalesInvoice getSalesInvoiceById(Long id) {
//        return salesInvoiceRepository.getById(id);
//    }


}
