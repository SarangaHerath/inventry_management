package com.novig.agency_management_system.repository;

import com.novig.agency_management_system.entity.SalesInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@EnableJpaRepositories
public interface SalesInvoiceRepo extends JpaRepository<SalesInvoice, Long> {
    List<SalesInvoice> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
