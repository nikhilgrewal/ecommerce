package com.project.entity;

import com.project.dao.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Table(name = "product")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order extends BaseEntity {

    @Column(unique = true, nullable = false)
    private UUID referenceId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email", nullable = false) // Foreign key to User
    private User user;  // here we are using email as user id
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProduct> orderProducts = new ArrayList<>();
}
