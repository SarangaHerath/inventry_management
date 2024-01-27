package com.novig.agency_management_system.repository;

import com.novig.agency_management_system.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface ProductRepo extends JpaRepository<Product, Long> {
//    List<Product> findBySaleInvoiceId(Long saleInvoiceId);
    List<Product> findByCategoryCategoryId(Long categoryId);

    List<Product> findByCategory_CategoryId(Long categoryId);
}
