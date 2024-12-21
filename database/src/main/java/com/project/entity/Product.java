package com.project.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "product")
@Entity
@Getter
@Setter
@Builder
public class Product extends BaseEntity {

    @Column(unique = true, nullable = false)
    private UUID referenceId;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private boolean deleted;

}
