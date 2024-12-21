package com.project.utils;

import com.project.dao.OrderProductView;
import com.project.dao.OrderView;
import com.project.dao.ProductView;
import com.project.dao.UserView;
import com.project.entity.Order;
import com.project.entity.OrderProduct;
import com.project.entity.Product;
import com.project.entity.User;

public class DaoToEntityMapper {
    public static Product convertProductViewToProduct(ProductView productView) {
        return Product.builder()
                .referenceId(productView.getReferenceId())
                .description(productView.getDescription())
                .name(productView.getName())
                .stock(productView.getStock())
                .price(productView.getPrice())
                .build();
    }

    public static User convertUserViewToUser(UserView userView) {
        return User.builder()
                .email(userView.getEmail())
                .name(userView.getName())
                .password(userView.getPassword())
                .build();
    }

    public static Order convertOrderViewToOrder(OrderView orderView) {
        return Order.builder()
                .referenceId(orderView.getReferenceId())
                .user(convertUserViewToUser(orderView.getUserView()))
                .orderProducts(orderView.getOrderProductViewList().stream()
                        .map(DaoToEntityMapper::convertOrderProductViewToOrderProduct)
                        .toList())
                .amount(orderView.getAmount())
                .status(orderView.getStatus())
                .build();
    }

    public static OrderProduct convertOrderProductViewToOrderProduct(OrderProductView orderProductView) {
        return OrderProduct.builder()
                .product(convertProductViewToProduct(orderProductView.getProductView()))
                .price(orderProductView.getPrice())
                .quantity(orderProductView.getQuantity())
                .build();
    }
}
