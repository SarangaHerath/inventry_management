package com.novig.agency_management_system.repository;

import com.novig.agency_management_system.entity.DeliveryRoute;
import com.novig.agency_management_system.entity.SalesInvoice;
import com.novig.agency_management_system.entity.SalesInvoiceDetails;
import com.novig.agency_management_system.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface SalesInvoiceDetailsRepo extends JpaRepository<SalesInvoiceDetails, Long> {

}
