package com.novig.agency_management_system.repository;

import com.novig.agency_management_system.entity.ChequeDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@EnableJpaRepositories
public interface ChequeDetailsRepo extends JpaRepository<ChequeDetails, Long> {

    List<ChequeDetails> findByReceivedDateBetween(LocalDate fromDate, LocalDate toDate);
}
