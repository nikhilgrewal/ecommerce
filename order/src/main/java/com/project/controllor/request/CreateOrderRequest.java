package com.project.controllor.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.dao.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateOrderRequest {
    private OrderStatus orderStatus = OrderStatus.CREATED;
    private BigDecimal amount;
    private List<ProductRequest> orderProducts = new ArrayList<>();


    @Data
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ProductRequest {
        private UUID referenceId;
        private Integer quantity;
        private BigDecimal price;
    }
}
