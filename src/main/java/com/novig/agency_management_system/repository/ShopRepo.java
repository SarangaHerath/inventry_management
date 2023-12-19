package com.novig.agency_management_system.repository;

import com.novig.agency_management_system.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface ShopRepo  extends JpaRepository<Shop,Long> {
    List<Shop> findByDeliveryRouteId(Long deliveryRouteId);
}
