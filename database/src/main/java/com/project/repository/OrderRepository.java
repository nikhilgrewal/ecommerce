package com.project.repository;

import com.project.dao.OrderStatus;
import com.project.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByReferenceId(UUID orderReferenceId);

    @Query("SELECT o FROM Order o WHERE o.createdAt < :cutOffTime AND o.status = :orderStatus")
    List<Order> findAllByOrderStatusAndCreatedAt(@Param("cutOffTime") LocalDateTime cutOffTime, @Param("orderStatus") OrderStatus orderStatus);
}
