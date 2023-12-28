package com.novig.agency_management_system.repository;

import com.novig.agency_management_system.entity.FreeIssue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FreeIssueRepo extends JpaRepository<FreeIssue,Long> {
}
