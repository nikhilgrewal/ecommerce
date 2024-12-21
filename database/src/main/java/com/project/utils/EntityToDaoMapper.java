package com.project.utils;

import com.project.dao.OrderProductView;
import com.project.dao.OrderView;
import com.project.dao.ProductView;
import com.project.dao.UserView;
import com.project.entity.Order;
import com.project.entity.OrderProduct;
import com.project.entity.Product;
import com.project.entity.User;

public class EntityToDaoMapper {
    public static ProductView convertProductToProductView(Product product){
        return ProductView.builder()
                .referenceId(product.getReferenceId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .stock(product.getStock())
                .updatedAt(product.getUpdatedAt())
                .createdAt(product.getCreatedAt())
                .build();
    }

    public static UserView convertUserToUserView(User user){
        return UserView.builder()
                .email(user.getEmail())
                .name(user.getName())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .password(user.getPassword())
                .orderList(user.getOrders().stream().map(EntityToDaoMapper::convertOrderToOrderView).toList())
                .build();
    }

    public static OrderView convertOrderToOrderView(Order order){
        return OrderView.builder()
                .amount(order.getAmount())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .status(order.getStatus())
                .userView(convertUserToUserView(order.getUser()))
                .referenceId(order.getReferenceId())
                .orderProductViewList(order.getOrderProducts().stream()
                        .map(EntityToDaoMapper::convertOrderProductToOrderProductView)
                        .toList())
                .build();
    }

    public static OrderProductView convertOrderProductToOrderProductView(OrderProduct orderProduct){
        return OrderProductView.builder()
                .price(orderProduct.getPrice())
                .quantity(orderProduct.getQuantity())
                .productView(convertProductToProductView(orderProduct.getProduct()))
                .build();
    }
}
