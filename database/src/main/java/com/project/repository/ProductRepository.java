package com.project.repository;

import com.project.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByReferenceId(UUID referenceID);

    @Query("SELECT p FROM Product p WHERE p.isDeleted = false AND " +
            "(:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
            "(:stock IS NULL OR p.stock >= :stock)")
    Page<Product> findAllProductsWithFilters(@Param("name") String name,
            @Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice,
            @Param("stock") Integer stock, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.isDeleted = false AND p.stock < :stock")
    List<Product> findAllProductWithStock(@Param("stock") Integer stock);
}

