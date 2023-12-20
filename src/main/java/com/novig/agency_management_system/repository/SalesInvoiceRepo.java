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
    @Query("SELECT COALESCE(SUM(CASE WHEN s.paymentMethod = 'Cheque' THEN s.total ELSE 0 END), 0) " +
            "FROM SalesInvoice s WHERE s.date = :date")
    Double getChequeTotalByDate(@Param("date") LocalDate date);

    @Query("SELECT COALESCE(SUM(CASE WHEN s.paymentMethod = 'Credit' THEN s.total ELSE 0 END), 0) " +
            "FROM SalesInvoice s WHERE s.date = :date")
    Double getCreditTotalByDate(@Param("date") LocalDate date);

    @Query("SELECT COALESCE(SUM(CASE WHEN s.paymentMethod = 'Cash' THEN s.total ELSE 0 END), 0) " +
            "FROM SalesInvoice s WHERE s.date = :date")
    Double getCashTotalByDate(@Param("date") LocalDate date);

    @Query("SELECT COALESCE(SUM(s.total), 0) FROM SalesInvoice s WHERE s.date = :date")
    Double getTotalByDate(@Param("date") LocalDate date);
}
