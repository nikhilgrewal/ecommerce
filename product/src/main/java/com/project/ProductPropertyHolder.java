package com.project;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class ProductPropertyHolder {

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${product.low.stock.threshold}")
    private Integer productLowStockThreshold;

}
