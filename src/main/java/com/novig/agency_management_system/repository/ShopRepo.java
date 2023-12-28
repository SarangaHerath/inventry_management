package com.novig.agency_management_system.repository;

import com.novig.agency_management_system.entity.DeliveryRoute;
import com.novig.agency_management_system.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShopRepo extends JpaRepository<Shop, Long> {

    List<Shop> findByDeliveryRoute(DeliveryRoute deliveryRoute);

    List<Shop> findByDeliveryRouteId(Long deliveryRouteId);

    @Query("SELECT s FROM Shop s LEFT JOIN FETCH s.deliveryRoute WHERE s.shopId = :shopId")
    Optional<Shop> findByIdWithDeliveryRoute(@Param("shopId") Long shopId);
}
