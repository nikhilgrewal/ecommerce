package com.project.service;

import com.project.controllor.request.CreateOrderRequest;
import com.project.dao.*;
import com.project.queryservice.OrderQueryService;
import com.project.queryservice.ProductQueryService;
import com.project.queryservice.UserQueryService;
import com.project.response.Response;
import io.micrometer.common.util.StringUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.project.response.Response.*;
import static com.project.utils.OrderErrorMessages.*;
import static com.project.utils.OrderMapper.convertCreateOrderRequestToOrderView;

@Service
@Log4j2
public class OrderService {

    private final OrderQueryService orderQueryService;
    private final UserQueryService userQueryService;
    private final ProductQueryService productQueryService;

    @Autowired
    public OrderService(OrderQueryService orderQueryService, UserQueryService userQueryService, ProductQueryService productQueryService) {
        this.orderQueryService = orderQueryService;
        this.userQueryService = userQueryService;
        this.productQueryService = productQueryService;
    }

    public Map<Response, Object> createOrder(String userId, CreateOrderRequest createOrderRequest) {
        Optional<UserView> userView = userQueryService.fetchUserByEmailId(userId);
        if (userView.isEmpty()) {
            return Map.of(ERROR, UNAUTHROIZED_USER, ERROR_CODE, HttpStatus.UNAUTHORIZED);
        }
        OrderView orderView = convertCreateOrderRequestToOrderView(createOrderRequest);
        String productRequestValid = validateAndUpdateOrderProductRequest(orderView.getOrderProductViewList());
        if (!StringUtils.isEmpty(productRequestValid)) {
            return switch (productRequestValid) {
                case PRODUCT_OUT_OF_STOCK:
                    yield Map.of(ERROR, productRequestValid, ERROR_CODE, HttpStatus.BAD_REQUEST);
                default:
                    yield Map.of(ERROR, productRequestValid, ERROR_CODE, HttpStatus.UNAUTHORIZED);
            };
        }
        orderView.setUserView(userView.get());
        String orderReferenceId = orderQueryService.createOrder(orderView);
        return Map.of(DATA, orderReferenceId);
    }

    public Map<Response, Object> updateOrderStatus(String userId, UUID orderReferenceId, OrderStatus orderStatus) {
        Optional<UserView> userView = userQueryService.fetchUserByEmailId(userId);
        if (userView.isEmpty()) {
            return Map.of(ERROR, UNAUTHROIZED_USER, ERROR_CODE, HttpStatus.UNAUTHORIZED);
        }

        return orderQueryService.updateOrder(orderReferenceId, orderStatus)
                .<Map<Response, Object>>map(view -> Map.of(DATA, view))
                .orElseGet(() -> Map.of(ERROR, ORDER_NOT_FOUND, ERROR_CODE, HttpStatus.NOT_FOUND));
    }

    public Map<Response, Object> fetchAllOrderForUser(String userId) {
        return userQueryService.fetchUserByEmailId(userId)
                .<Map<Response, Object>>map(userView -> Map.of(DATA, userView.getOrderList()))
                .orElseGet(() -> Map.of(ERROR, UNAUTHROIZED_USER, ERROR_CODE, HttpStatus.UNAUTHORIZED));
    }

    public Map<Response, Object> deleteOrderPermanently(UUID orderReferenceId) {
        return orderQueryService.deleteOrderPermanently(orderReferenceId)
                .<Map<Response, Object>>map(orderView -> Map.of(DATA, orderView))
                .orElseGet(() -> Map.of(ERROR, ORDER_NOT_FOUND, ERROR_CODE, HttpStatus.NOT_FOUND));
    }

    private String validateAndUpdateOrderProductRequest(List<OrderProductView> orderProductViewList) {
        return orderProductViewList.stream()
                .map(this::validateAndUpdateProductRequest)
                .filter(validationResult -> !validationResult.isEmpty())
                .findFirst()
                .orElse(""); // Return empty string if all products are valid
    }

    private String validateAndUpdateProductRequest(OrderProductView orderProductView) {
        Optional<ProductView> productView = productQueryService
                .fetchProductByReferenceId(orderProductView.getProductView().getReferenceId());

        if (productView.isEmpty()) {
            return UNAUTHROIZED_PRODUCT; // Return error if the product is unauthorized
        }

        if (productView.get().getStock() < orderProductView.getQuantity()) {
            return PRODUCT_OUT_OF_STOCK; // Return error if stock is insufficient
        }

        productView.get().setStock(productView.get().getStock() - orderProductView.getQuantity());
        orderProductView.setProductView(productView.get());
        return ""; // Return empty string if the product is valid
    }

}
