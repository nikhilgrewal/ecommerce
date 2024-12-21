package com.project.dao;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class OrderView {
    private UUID referenceId;
    private UserView userView;
    private BigDecimal amount;
    private OrderStatus status;
    private List<OrderProductView> orderProductViewList;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
