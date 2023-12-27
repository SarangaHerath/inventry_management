package com.novig.agency_management_system.repository;

import com.novig.agency_management_system.entity.SalesInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
@EnableJpaRepositories
public interface SalesInvoiceRepo extends JpaRepository<SalesInvoice, Long> {
    List<SalesInvoice> findByDateBetween(LocalDate startDate, LocalDate endDate);

    List<SalesInvoice> findByDate(LocalDate date);

    long countByDate(LocalDate date);

    @Query("SELECT COUNT(si) FROM SalesInvoice si WHERE si.date BETWEEN :startDate AND :endDate")
    long countByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT SUM(si.total) FROM SalesInvoice si WHERE si.date BETWEEN :startDate AND :endDate")
    Double getTotalSaleCountByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT SUM(si.discount) FROM SalesInvoice si WHERE si.date BETWEEN :startDate AND :endDate")
    Double getTotalDiscountCountByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT SUM(si.freeItems) FROM SalesInvoice si WHERE si.date BETWEEN :startDate AND :endDate")
    Double getTotalFreeItemsCountByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT SUM(si.returnValue) FROM SalesInvoice si WHERE si.date BETWEEN :startDate AND :endDate")
    Double getTotalReturnValuesCountByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
