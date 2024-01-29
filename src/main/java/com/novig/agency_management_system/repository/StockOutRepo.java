package com.novig.agency_management_system.repository;

import com.novig.agency_management_system.entity.Product;
import com.novig.agency_management_system.entity.StockOut;
import com.novig.agency_management_system.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface StockOutRepo extends JpaRepository<StockOut, Long> {

    List<StockOut> findByProduct_ProductId(Long productId);


    List<StockOut> findByProduct_Category_CategoryId(Long categoryId);


    Optional<StockOut> findByProductAndVehicle(Product product, Vehicle vehicle);
}
