package com.project.controllor.request;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class UpdateProductRequest {

    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
}
