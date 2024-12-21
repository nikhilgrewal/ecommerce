package com.project.dao;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderProductView {
    private ProductView productView;
    private Integer quantity;
    private BigDecimal price;
}
