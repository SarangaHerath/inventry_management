package com.novig.agency_management_system.repository;

import com.novig.agency_management_system.entity.SalesInvoiceDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface SalesInvoiceDetailsRepo extends JpaRepository<SalesInvoiceDetails, Long> {
}
