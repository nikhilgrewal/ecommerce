package com.project.dao;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class ProductView {

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID referenceId;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
}
