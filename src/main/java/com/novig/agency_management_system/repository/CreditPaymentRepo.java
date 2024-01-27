package com.novig.agency_management_system.repository;

import com.novig.agency_management_system.entity.CreditPaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@EnableJpaRepositories
public interface CreditPaymentRepo extends JpaRepository<CreditPaymentDetails, Long> {
    List<CreditPaymentDetails> findByBillDateBetween(LocalDate fromDate, LocalDate toDate);

    @Query("SELECT COALESCE(SUM(cpd.creditAmount + cpd.paidAmount), 0) " +
            "FROM CreditPaymentDetails cpd " +
            "WHERE cpd.billDate BETWEEN :startDate AND :endDate")
    Double getTotalCreditValuesCountByDateRange(@Param("startDate") LocalDate startDate,
                                                @Param("endDate") LocalDate endDate);
}

