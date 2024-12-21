package com.project.controllor.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;

@Data
@Builder
public class CreateProductRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NonNull
    private BigDecimal price;
    @NotBlank
    private Integer stock;

}
