package com.project.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "product")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseEntity {

    @Column(unique = true, nullable = false)
    private UUID referenceId;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private boolean deleted;

}
