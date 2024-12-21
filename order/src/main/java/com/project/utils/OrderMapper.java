package com.project.utils;

import com.project.controllor.request.CreateOrderRequest;
import com.project.dao.OrderProductView;
import com.project.dao.OrderView;
import com.project.dao.ProductView;

public class OrderMapper {

    public static OrderView convertCreateOrderRequestToOrderView(CreateOrderRequest createOrderRequest) {
        return OrderView.builder()
                .status(createOrderRequest.getOrderStatus())
                .amount(createOrderRequest.getAmount())
                .orderProductViewList(createOrderRequest.getOrderProducts().stream()
                        .map(OrderMapper::convertProductRequestToOderProductView)
                        .toList())
                .build();
    }

    public static OrderProductView convertProductRequestToOderProductView(CreateOrderRequest.ProductRequest productRequest) {
        return OrderProductView.builder()
                .productView(ProductView.builder().referenceId(productRequest.getReferenceId()).build())
                .quantity(productRequest.getQuantity())
                .price(productRequest.getPrice())
                .build();
    }
}
