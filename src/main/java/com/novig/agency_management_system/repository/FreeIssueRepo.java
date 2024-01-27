package com.novig.agency_management_system.repository;

import com.novig.agency_management_system.entity.FreeIssue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FreeIssueRepo extends JpaRepository<FreeIssue,Long> {
//    List<FreeIssue> findAllBy(LocalDate fromDate, LocalDate toDate);

    @Query("SELECT fi FROM FreeIssue fi " +
            "JOIN fi.salesInvoice si " +
            "WHERE si.date BETWEEN :fromDate AND :toDate " +
            "AND si.freeItems > 0")
    List<FreeIssue> findFreeIssuesWithFreeItemsBetweenDates(@Param("fromDate") LocalDate fromDate,
                                                            @Param("toDate") LocalDate toDate);
}
